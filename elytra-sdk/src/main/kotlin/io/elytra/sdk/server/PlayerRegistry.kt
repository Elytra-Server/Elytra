package io.elytra.sdk.server

import com.mojang.authlib.GameProfile
import io.elytra.api.entity.Player
import io.elytra.api.entity.PlayerMode
import io.elytra.api.events.EventBus
import io.elytra.api.registry.Registry
import io.elytra.api.world.Position
import io.elytra.api.world.enums.WorldType
import io.elytra.sdk.entity.ElytraPlayer
import io.elytra.sdk.events.player.PlayerJoinEvent
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.message.login.LoginSuccessMessage
import io.elytra.sdk.network.protocol.message.play.outbound.ChunkDataMessage
import io.elytra.sdk.network.protocol.message.play.outbound.HeldItemChangeMessage
import io.elytra.sdk.network.protocol.message.play.outbound.JoinGameMessage
import io.elytra.sdk.network.protocol.message.play.outbound.PlayerPositionAndLookMessage
import io.elytra.sdk.network.protocol.packets.Protocol
import io.elytra.sdk.world.ElytraChunk
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class PlayerRegistry : Registry<String, Player> {

    private val mutex = Mutex()
    private var players: Set<Player> = ConcurrentHashMap.newKeySet()
    private var currentId: AtomicInteger = AtomicInteger(0)

    fun initialize(session: NetworkSession, gameProfile: GameProfile) {
        val playerMode = if (gameProfile.isComplete) PlayerMode.PREMIUM else PlayerMode.OFFLINE

        val player = ElytraPlayer(
            currentId.getAndIncrement(),
            session.sessionId,
            gameProfile.name,
            gameProfile,
            playerMode,
            session.isActive,
            banned = false,
            position = Position.EMPTY,
            world = Elytra.server.mainWorld
        )

        runBlocking(CoroutineName("player-registry-worker")) {
            withContext(Dispatchers.IO) {
                add(player)
            }
        }

        session.send(LoginSuccessMessage(gameProfile))
        session.protocol(Protocol.PLAY)

        val spawn = Elytra.server.mainWorld.spawnPoint

        val joinMessage = JoinGameMessage(
            player.id,
            player.gamemode,
            0,
            0,
            Elytra.server.serverDescriptor.options.maxPlayers,
            WorldType.FLAT.prettyName,
            Elytra.server.serverDescriptor.world.renderDistance,
            false,
            false)

        session.send(joinMessage)

        GlobalScope.launch(Dispatchers.Default) {
            for (x in -1 until ((spawn.x * 2) / 16 + 1).toInt()) {
                for (z in -1 until ((spawn.z * 2) / 16 + 1).toInt()) {
                    val chunk = Elytra.server.mainWorld.getChunkAt(x, z)
                    session.send(ChunkDataMessage(x, z, chunk as ElytraChunk))
                }
            }
        }

        session.send(HeldItemChangeMessage(0))
        session.send(PlayerPositionAndLookMessage(spawn))

        EventBus.post(PlayerJoinEvent(player))
    }

    override suspend fun add(target: Player): Unit = mutex.withLock {
        players = players.plusElement(target)
        currentId.getAndIncrement()
    }

    override suspend fun remove(target: Player): Unit = mutex.withLock {
        players = players.minusElement(target)
        currentId.getAndDecrement()
    }

    override fun get(key: String): Player? {
        return players.first { player -> player.gameProfile.name == key }
    }

    override fun has(key: String): Boolean {
        return players.any { player -> player.gameProfile.name == key }
    }

    fun get(session: NetworkSession): Player? {
        return players.first { player -> (player as ElytraPlayer).sessionId == session.sessionId }
    }

    fun get(target: Int): Player? {
        return players.first { player -> (player as ElytraPlayer).id == target }
    }

    override fun iterator(): Iterator<Player> {
        return players.iterator()
    }

    override fun size(): Int {
        return players.size
    }

    override fun clear() {
    }
}

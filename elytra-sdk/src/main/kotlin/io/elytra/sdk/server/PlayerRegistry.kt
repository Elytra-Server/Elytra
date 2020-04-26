package io.elytra.sdk.server

import com.mojang.authlib.GameProfile
import io.elytra.api.entity.player.Player
import io.elytra.api.entity.player.PlayerReliability
import io.elytra.api.registry.Registry
import io.elytra.api.world.Position
import io.elytra.sdk.entity.ElytraPlayer
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.message.login.LoginSuccessMessage
import io.elytra.sdk.network.protocol.packets.Protocol
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class PlayerRegistry : Registry<String, Player> {

    private val mutex = Mutex()
    private var players: Set<Player> = ConcurrentHashMap.newKeySet()
    private var currentId: AtomicInteger = AtomicInteger(0)

    fun initialize(session: NetworkSession, gameProfile: GameProfile) {
        val playerMode = if (gameProfile.isComplete) PlayerReliability.PREMIUM else PlayerReliability.OFFLINE

        val player = ElytraPlayer(
            currentId.getAndIncrement(),
            session.sessionId,
            gameProfile.name,
            gameProfile,
            playerMode,
            session.isActive,
            banned = false,
            position = Position(3.0, 1.5, 3.0, 0.0f, 0.0f),
            world = Elytra.server.mainWorld
        )

        runBlocking(CoroutineName("player-registry-worker")) {
            withContext(Dispatchers.IO) {
                add(player)
            }
        }

        session.send(LoginSuccessMessage(gameProfile))
        session.protocol(Protocol.PLAY)

        player.join()
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

    fun getAll(): Set<Player> {
        return players
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

package io.elytra.sdk.server

import com.mojang.authlib.GameProfile
import io.elytra.api.entity.Player
import io.elytra.api.entity.PlayerMode
import io.elytra.api.events.EventBus
import io.elytra.api.registry.Registry
import io.elytra.api.world.Position
import io.elytra.sdk.entity.ElytraPlayer
import io.elytra.sdk.events.PlayerJoinEvent
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.message.login.LoginSuccessMessage
import io.elytra.sdk.network.protocol.message.play.outbound.HeldItemChangeMessage
import io.elytra.sdk.network.protocol.message.play.outbound.JoinGameMessage
import io.elytra.sdk.network.protocol.message.play.outbound.PlayerPositionAndLookMessage
import io.elytra.sdk.network.protocol.packets.Protocol
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class PlayerRegistry(
    private var players: Set<Player> = ConcurrentHashMap.newKeySet(),
    private var currentId: AtomicInteger = AtomicInteger(0)
) : Registry<Player, String> {

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
            position = Position.EMPTY
        )

        add(player)

        session.send(LoginSuccessMessage(gameProfile))
        session.protocol(Protocol.PLAY)

        val joinMessage = JoinGameMessage(
            player.id,
            player.gamemode,
            0,
            0,
            2,
            "flat",
            32,
            false,
            false)

        session.send(joinMessage)
        session.send(HeldItemChangeMessage(4))
        session.send(PlayerPositionAndLookMessage(player.position))

        EventBus.post(PlayerJoinEvent(player))
    }

    override fun add(target: Player) {
        players = players.plusElement(target)
        currentId.getAndIncrement()
    }

    override fun remove(target: Player) {
        players = players.minusElement(target)
        currentId.getAndDecrement()
    }

    override fun get(username: String): Player? {
        return players.first { player -> player.gameProfile.name == username }
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

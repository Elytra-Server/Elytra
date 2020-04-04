package io.elytra.sdk.server

import com.mojang.authlib.GameProfile
import io.elytra.api.chat.Colors
import io.elytra.api.chat.TextComponent
import io.elytra.api.entity.Player
import io.elytra.api.entity.PlayerMode
import io.elytra.api.registry.Registry
import io.elytra.sdk.entity.ElytraPlayer
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.message.login.LoginSuccessMessage
import io.elytra.sdk.network.protocol.message.play.*
import io.elytra.sdk.network.protocol.packets.Protocol
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class PlayerRegistry(
    private var players: Set<Player> = ConcurrentHashMap.newKeySet(),
    private var currentId: AtomicInteger = AtomicInteger(0)
) : Registry<Player, String> {

    fun initialize(session: NetworkSession, gameProfile: GameProfile) {
        val player = ElytraPlayer(
            currentId.getAndIncrement(),
            session.sessionId,
            gameProfile.name,
            gameProfile,
            if (gameProfile.isComplete) PlayerMode.PREMIUM else PlayerMode.OFFLINE,
            online = false,
            banned = false)

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
        val positionMessage = PlayerPosLookMessage(player.position.x, player.position.y, player.position.z, player.position.yaw, player.position.pitch, 0x01, player.id)

        session.send(joinMessage)
        session.send(HeldItemChangeMessage(4))
        session.send(positionMessage)

        Elytra.sendPacketToAll(OutboundChatMessage(TextComponent("${Colors.YELLOW}${player.displayName} joined the game"), 1))
        Elytra.sendPacketToAll(PlayerListItemMessage(Action.ADD_PLAYER, listOf(AddPlayerData(0, player.gamemode, player.gameProfile!!, TextComponent(player.displayName)))))
        players.iterator().forEach { it: Player ->
            session.send(PlayerListItemMessage(Action.ADD_PLAYER, listOf(AddPlayerData(0, it.gamemode, player.gameProfile!!, TextComponent(it.displayName)))))
        }
    }

    override fun add(target: Player) {
        players = players.plusElement(target)
        currentId.getAndIncrement()
    }

    override fun remove(target: Player) {
        players = players.minusElement(target)
        currentId.getAndDecrement()
    }

    override fun get(target: String): Player? {
        return players.first { player -> player.gameProfile!!.name == target }
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

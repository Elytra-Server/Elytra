package io.elytra.sdk.server

import com.mojang.authlib.GameProfile
import io.elytra.api.chat.ChatComponent
import io.elytra.api.chat.Colors
import io.elytra.api.entity.Player
import io.elytra.api.entity.PlayerMode
import io.elytra.api.registry.Registry
import io.elytra.api.world.enums.Difficulty
import io.elytra.sdk.entity.ElytraPlayer
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.ProtocolInfo
import io.elytra.sdk.network.protocol.message.login.LoginSuccessMessage
import io.elytra.sdk.network.protocol.message.play.*
import io.elytra.sdk.network.protocol.packets.Protocol
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.Unpooled
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class PlayerRegistry(
	private var players: Set<Player> = ConcurrentHashMap.newKeySet(),
	private var currentId: AtomicInteger = AtomicInteger(0)
) : Registry<Player, String>{

	fun initialize(session: NetworkSession, gameProfile: GameProfile){
		var player: Player = ElytraPlayer(
			currentId.getAndIncrement(),
			session.sessionId,
			gameProfile.name,
			gameProfile,
			if(gameProfile.isComplete) PlayerMode.PREMIUM else PlayerMode.OFFLINE,
			online = false,
			banned = false,
			exp = 0,
			expLevel = 0)
		players = players.plus(player)

		//TODO Add gameProfile in cache

		session.send(LoginSuccessMessage(gameProfile))
		session.protocol(Protocol.PLAY)

		val joinMessage = JoinGameMessage(1, player.gamemode, 0, Difficulty.NORMAL, 2, "flat", false)
		val positionMessage = PlayerPosLookMessage(player.position.x, player.position.y, player.position.z, player.position.yaw, player.position.pitch, 0x01, 153)

		session.send(joinMessage)
		val version = Unpooled.buffer()
		version.minecraft.writeString(ProtocolInfo.MINECRAFT_VERSION)
		session.send(CustomPayloadMessage("MC|Brand",version))
		session.send(HeldItemChangeMessage(4))
		//session.send(EntityStatusMessage(1, 24)) //Crash client
		session.send(positionMessage)

		Elytra.sendPacketToAll(OutboundChatMessage(ChatComponent("${Colors.YELLOW} ${player.displayName} joined the game"),1))
		Elytra.sendPacketToAll(PlayerListItemMessage(Action.ADD_PLAYER, listOf(AddPlayerData(0,player.gamemode,player.gameProfile!!, ChatComponent(player.displayName)))))

		players.iterator().forEach { it: Player ->
			session.send(PlayerListItemMessage(Action.ADD_PLAYER, listOf(AddPlayerData(0,it.gamemode,player.gameProfile!!, ChatComponent(it.displayName)))))
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
		return players.first { player -> player.gameProfile!!.name == target };
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

package io.elytra.sdk.network.protocol.handlers.login

import com.mojang.authlib.GameProfile
import io.elytra.api.entity.Player
import io.elytra.api.world.Difficulty
import io.elytra.api.world.GameMode
import io.elytra.api.world.Location
import io.elytra.sdk.entity.ElytraPlayer
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.handlers.ElytraMessageHandler
import io.elytra.sdk.network.protocol.message.LoginStartMessage
import io.elytra.sdk.network.protocol.message.login.LoginSuccessMessage
import io.elytra.sdk.network.protocol.message.play.EntityStatusMessage
import io.elytra.sdk.network.protocol.message.play.HeldItemChangeMessage
import io.elytra.sdk.network.protocol.message.play.JoinGameMessage
import io.elytra.sdk.network.protocol.message.play.PlayerPosLookMessage
import io.elytra.sdk.network.protocol.packets.PlayPacket
import io.elytra.sdk.server.Elytra
import java.util.*

class LoginStartHandler : ElytraMessageHandler<LoginStartMessage>() {
	override fun handle(session: NetworkSession, message: LoginStartMessage) {
		val username: String = message.username
		val uuid = UUID.randomUUID()
		val gameProfile: GameProfile = GameProfile(uuid,username)

		println("Login has started to user - ${message.username}")

		if(!session.isActive){
			session.onDisconnect();
			return
		}

		session.send(LoginSuccessMessage(uuid.toString(), username))
		session.setProtocol(PlayPacket())

		val player: Player = ElytraPlayer(session.sessionId,username,uuid,gameProfile,true,false,0,0, Location.EMPTY,GameMode.SURVIVAL)

		val joinMessage = JoinGameMessage(1, player.gamemode, 0, Difficulty.NORMAL, 1, "flat", false)
		val positionMessage = PlayerPosLookMessage(player.location.x, player.location.y, player.location.z, player.location.yaw, player.location.pitch, 0x01, 1)

		session.send(joinMessage)
		session.send(HeldItemChangeMessage(8))
		session.send(EntityStatusMessage(1, 24))
		session.send(positionMessage)

	}
}

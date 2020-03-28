package io.elytra.sdk.network.protocol.handlers.login

import com.mojang.authlib.GameProfile
import io.elytra.api.world.Difficulty
import io.elytra.api.world.GameMode
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.SessionState
import io.elytra.sdk.network.protocol.handlers.ElytraMessageHandler
import io.elytra.sdk.network.protocol.message.login.EncryptionRequestMessage
import io.elytra.sdk.network.protocol.message.login.LoginStartMessage
import io.elytra.sdk.network.protocol.message.login.LoginSuccessMessage
import io.elytra.sdk.network.protocol.message.play.EntityStatusMessage
import io.elytra.sdk.network.protocol.message.play.HeldItemChangeMessage
import io.elytra.sdk.network.protocol.message.play.JoinGameMessage
import io.elytra.sdk.network.protocol.message.play.PlayerPosLookMessage
import io.elytra.sdk.network.protocol.packets.PlayPacket
import io.elytra.sdk.server.Elytra
import org.apache.commons.lang3.Validate
import java.nio.charset.StandardCharsets
import java.util.*

class LoginStartHandler : ElytraMessageHandler<LoginStartMessage>() {
	override fun handle(session: NetworkSession, message: LoginStartMessage) {
		Validate.validState(session.sessionState == SessionState.HELLO, "Unexpected hello packet")
		println("Login has started to user - ${message.gameProfile.name}")

		//Define default gameProfile
		session.gameProfile = message.gameProfile

		if(!session.isActive){
			session.onDisconnect();
			return
		}

		//Elytra.server.playerRegistry.initialize(session,GameProfile(UUID.nameUUIDFromBytes(("OfflinePlayer:" + message.gameProfile.name.toLowerCase()).toByteArray(StandardCharsets.UTF_8)),  message.gameProfile.name), false)

		session.sessionState = SessionState.KEY
		session.send(EncryptionRequestMessage("",Elytra.server.keypair.public,session.verifyToken))
	}
}

package io.elytra.sdk.network.protocol.handlers.login

import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.handlers.ElytraMessageHandler
import io.elytra.sdk.network.protocol.message.LoginStartMessage
import io.elytra.sdk.network.protocol.message.login.LoginSuccessMessage
import io.elytra.sdk.network.protocol.packets.PlayPacket
import java.util.*

class LoginStartHandler : ElytraMessageHandler<LoginStartMessage>() {
	override fun handle(session: NetworkSession, message: LoginStartMessage) {
		val username: String = message.username
		val uuid = UUID.randomUUID()

		println("Login has started to user - ${message.username}")

		if(!session.isActive){
			session.onDisconnect();
			return
		}

		session.send(LoginSuccessMessage(uuid.toString(), username))
		session.setProtocol(PlayPacket())
		//session.assignPlayer(InbPlayer(username, uuid, gameProfile = null))
	}
}

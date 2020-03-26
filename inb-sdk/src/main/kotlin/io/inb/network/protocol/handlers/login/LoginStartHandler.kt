package io.inb.network.protocol.handlers.login

import io.inb.network.NetworkSession
import io.inb.network.protocol.handlers.InbMessageHandler
import io.inb.network.protocol.message.LoginStartMessage
import io.inb.network.protocol.message.login.LoginSuccessMessage
import io.inb.network.protocol.packets.PlayPacket
import java.util.*

class LoginStartHandler : InbMessageHandler<LoginStartMessage>() {
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

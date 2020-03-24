package io.inb.api.network.protocol.handlers.login

import com.flowpowered.network.MessageHandler
import io.inb.api.InbServer
import io.inb.api.network.NetworkSession
import io.inb.api.network.State
import io.inb.api.network.protocol.message.LoginStartMessage
import io.inb.api.network.protocol.message.login.LoginSuccessMessage
import java.util.*

class LoginStartHandler : MessageHandler<NetworkSession, LoginStartMessage>{
	override fun handle(session: NetworkSession, message: LoginStartMessage) {
		val name: String = message.username
		val uuid = UUID.randomUUID()

		session.state = State.PLAYING
		println("Login has started to user - ${message.username}")

		session.send(LoginSuccessMessage(uuid.toString(), name))
	}
}

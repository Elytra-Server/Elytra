package io.inb.api.network.protocol.handlers.login

import com.flowpowered.network.MessageHandler
import io.inb.api.entity.Player
import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.message.LoginStartMessage
import java.util.*

class LoginStartHandler : MessageHandler<NetworkSession, LoginStartMessage>{
	override fun handle(session: NetworkSession, message: LoginStartMessage) {
		val username: String = message.username
		val uuid = UUID.randomUUID()

		println("Login has started to user - ${message.username}")

		session.assignPlayer(Player(username, uuid))
	}
}

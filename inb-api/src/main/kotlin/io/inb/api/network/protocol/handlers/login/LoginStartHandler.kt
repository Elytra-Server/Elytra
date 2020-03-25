package io.inb.api.network.protocol.handlers.login

import io.inb.api.entity.InbPlayer
import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.handlers.InbMessageHandler
import io.inb.api.network.protocol.message.LoginStartMessage
import io.inb.api.server.InbServer
import io.inb.api.server.Server
import java.util.*

class LoginStartHandler : InbMessageHandler<LoginStartMessage>() {
	override fun handle(session: NetworkSession, message: LoginStartMessage) {
		val username: String = message.username
		val uuid = UUID.randomUUID()

		println("Login has started to user - ${message.username}")

		session.assignPlayer(InbPlayer(username, uuid, gameProfile = null))
	}
}

package io.inb.api.network.protocol.handlers.status

import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.handlers.InbMessageHandler
import io.inb.api.network.protocol.message.status.PingMessage

class PingHandler : InbMessageHandler<PingMessage>() {
	override fun handle(session: NetworkSession, message: PingMessage) {
		session.send(message)
		session.send(message)
		session.send(message)
		session.send(message)
	}
}

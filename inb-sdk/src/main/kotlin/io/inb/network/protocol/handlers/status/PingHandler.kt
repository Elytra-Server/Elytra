package io.inb.network.protocol.handlers.status

import io.inb.network.NetworkSession
import io.inb.network.protocol.handlers.InbMessageHandler
import io.inb.network.protocol.message.status.PingMessage

class PingHandler : InbMessageHandler<PingMessage>() {
	override fun handle(session: NetworkSession, message: PingMessage) {
		session.send(message)
		session.send(message)
		session.send(message)
		session.send(message)
	}
}

package io.elytra.sdk.network.protocol.handlers.status

import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.handlers.InbMessageHandler
import io.elytra.sdk.network.protocol.message.status.PingMessage

class PingHandler : InbMessageHandler<PingMessage>() {
	override fun handle(session: NetworkSession, message: PingMessage) {
		session.send(message)
		session.send(message)
		session.send(message)
		session.send(message)
	}
}

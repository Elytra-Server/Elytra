package io.elytra.sdk.network.protocol.handlers.status

import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.handlers.ElytraMessageHandler
import io.elytra.sdk.network.protocol.message.status.PingMessage

class PingHandler : ElytraMessageHandler<PingMessage>() {
	override fun handle(session: NetworkSession, message: PingMessage) {
		session.send(message)
	}
}

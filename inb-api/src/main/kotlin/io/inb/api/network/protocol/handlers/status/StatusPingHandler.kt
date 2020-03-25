package io.inb.api.network.protocol.handlers.status

import com.flowpowered.network.MessageHandler
import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.handlers.InbMessageHandler
import io.inb.api.network.protocol.message.status.StatusPingMessage

class StatusPingHandler : InbMessageHandler<StatusPingMessage>() {

	override fun handle(session: NetworkSession, message: StatusPingMessage) {
		session.send(message)
	}
}

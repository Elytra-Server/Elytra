package io.inb.api.network.protocol.handlers.status

import com.flowpowered.network.MessageHandler
import io.inb.api.network.InbSession
import io.inb.api.network.protocol.message.status.StatusPingMessage

class StatusPingHandler : MessageHandler<InbSession, StatusPingMessage> {

	override fun handle(session: InbSession, message: StatusPingMessage) {
		session.send(message)
	}
}

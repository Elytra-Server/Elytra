package io.inb.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import io.inb.network.NetworkSession
import io.inb.network.protocol.message.play.CustomPayloadMessage

class CustomPayloadHandler : MessageHandler<NetworkSession, CustomPayloadMessage> {

	override fun handle(session: NetworkSession, message: CustomPayloadMessage) {
		println("Custom Payload")
	}
}

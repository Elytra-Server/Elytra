package io.inb.api.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.message.play.ConfirmTeleportMessage
import io.inb.api.network.protocol.message.play.CustomPayloadMessage

class CustomPayloadHandler : MessageHandler<NetworkSession, CustomPayloadMessage> {

	override fun handle(session: NetworkSession, message: CustomPayloadMessage) {
		println("Custom Payload")
	}
}

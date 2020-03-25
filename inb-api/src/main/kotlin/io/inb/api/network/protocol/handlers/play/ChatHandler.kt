package io.inb.api.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.message.play.ChatMessage
import io.inb.api.network.protocol.message.play.CustomPayloadMessage

class ChatHandler : MessageHandler<NetworkSession, ChatMessage> {
	override fun handle(session: NetworkSession, message: ChatMessage) {
		println("Chat ${message.message}")
	}
}

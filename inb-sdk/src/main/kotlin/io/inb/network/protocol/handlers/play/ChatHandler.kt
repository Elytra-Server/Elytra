package io.inb.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import io.inb.network.NetworkSession
import io.inb.network.protocol.message.play.ChatMessage

class ChatHandler : MessageHandler<NetworkSession, ChatMessage> {
	override fun handle(session: NetworkSession, message: ChatMessage) {
		println("Chat ${message.message}")
	}
}

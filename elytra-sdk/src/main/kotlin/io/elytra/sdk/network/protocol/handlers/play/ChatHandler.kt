package io.elytra.sdk.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.message.play.ChatMessage

class ChatHandler : MessageHandler<NetworkSession, ChatMessage> {
	override fun handle(session: NetworkSession, message: ChatMessage) {
		println("Chat ${message.message}")
	}
}

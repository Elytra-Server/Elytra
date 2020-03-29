package io.elytra.sdk.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import io.elytra.api.chat.ChatComponent
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.message.play.ChatMessage
import io.elytra.sdk.network.protocol.message.play.OutboundChatMessage

class ChatHandler : MessageHandler<NetworkSession, ChatMessage> {
	override fun handle(session: NetworkSession, message: ChatMessage) {
		//TODO: Needs refactor
		session.send(OutboundChatMessage(ChatComponent(message.content), 0))
	}
}

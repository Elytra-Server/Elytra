package io.elytra.sdk.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import com.google.gson.Gson
import io.elytra.api.chat.ChatDescriptor
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.message.play.ChatMessage
import io.elytra.sdk.network.protocol.message.play.OutboundChatMessage

class ChatHandler : MessageHandler<NetworkSession, ChatMessage> {
	override fun handle(session: NetworkSession, message: ChatMessage) {
		//TODO: Needs refactor
		val json = Gson().toJson(ChatDescriptor(message.content))
		println(json)
		session.send(OutboundChatMessage(json, 0))
	}
}

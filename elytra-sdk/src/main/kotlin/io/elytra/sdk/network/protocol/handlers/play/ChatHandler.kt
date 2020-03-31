package io.elytra.sdk.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import io.elytra.api.chat.TextComponent
import io.elytra.api.chat.ChatMode
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.message.play.ChatMessage
import io.elytra.sdk.network.protocol.message.play.OutboundChatMessage

class ChatHandler : MessageHandler<NetworkSession, ChatMessage> {

	override fun handle(session: NetworkSession, message: ChatMessage) {
		val content = message.content.replace('&', '\u00A7')
		val textComponent = TextComponent("${session.gameProfile?.name}: $content")
		session.send(OutboundChatMessage(textComponent, ChatMode.PLAYER))
	}
}

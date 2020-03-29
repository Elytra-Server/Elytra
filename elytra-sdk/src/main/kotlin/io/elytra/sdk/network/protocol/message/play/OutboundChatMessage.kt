package io.elytra.sdk.network.protocol.message.play

import com.flowpowered.network.Message
import io.elytra.api.chat.ChatComponent
import io.elytra.api.utils.asJson

data class OutboundChatMessage(val content: String, val mode: Int) : Message {
	constructor(component: ChatComponent, mode: Int) : this(component.asJson(), mode)
}

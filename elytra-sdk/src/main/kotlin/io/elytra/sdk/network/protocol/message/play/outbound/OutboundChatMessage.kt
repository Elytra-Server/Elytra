package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message
import io.elytra.api.chat.TextComponent

data class OutboundChatMessage(val content: String, val mode: Int) : Message {
    constructor(component: TextComponent, mode: Int) : this(component.toJson(), mode)
}

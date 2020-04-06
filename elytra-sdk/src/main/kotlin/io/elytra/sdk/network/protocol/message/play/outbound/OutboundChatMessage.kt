package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message
import io.elytra.api.chat.TextComponent
import io.elytra.api.utils.asJson

data class OutboundChatMessage(val content: String, val mode: Int) : Message {
    constructor(component: TextComponent, mode: Int) : this(component.asJson(), mode)
}

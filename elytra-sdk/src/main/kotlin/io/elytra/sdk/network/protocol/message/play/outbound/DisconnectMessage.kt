package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message
import io.elytra.api.chat.TextComponent
import io.elytra.api.utils.Asyncable

data class DisconnectMessage(val textComponent: TextComponent) : Message, Asyncable {
    constructor(message: String) : this(TextComponent(message))
}

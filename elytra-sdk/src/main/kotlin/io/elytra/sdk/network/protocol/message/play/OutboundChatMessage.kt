package io.elytra.sdk.network.protocol.message.play

import com.flowpowered.network.Message

data class OutboundChatMessage(val content: String, val mode: Int) : Message

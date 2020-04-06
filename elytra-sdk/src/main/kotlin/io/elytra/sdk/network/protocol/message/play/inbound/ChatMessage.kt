package io.elytra.sdk.network.protocol.message.play.inbound

import com.flowpowered.network.Message

data class ChatMessage(val content: String) : Message

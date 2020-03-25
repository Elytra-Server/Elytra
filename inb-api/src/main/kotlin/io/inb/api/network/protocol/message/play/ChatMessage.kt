package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message
import io.netty.buffer.ByteBuf

data class ChatMessage(val message: String) : Message

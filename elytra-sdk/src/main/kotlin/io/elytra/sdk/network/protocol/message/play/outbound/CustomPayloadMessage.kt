package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message
import io.netty.buffer.ByteBuf

data class CustomPayloadMessage(
    val channel: String,
    val data: ByteBuf
) : Message

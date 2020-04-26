package io.elytra.sdk.network.protocol.codecs.play.outbound

import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.ChangeGameStateMessage
import io.netty.buffer.ByteBuf

class ChangeGameStateCodec : OutboundCodec<ChangeGameStateMessage>() {
    override fun encode(buffer: ByteBuf, message: ChangeGameStateMessage): ByteBuf {
        buffer.writeByte((message.type.toByte()).toInt())
        buffer.writeFloat(message.value)

        return buffer
    }
}

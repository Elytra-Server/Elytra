package io.elytra.sdk.network.protocol.codecs.play.outbound

import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.ChangeGameStateMessage
import io.netty.buffer.ByteBuf

class ChangeGameStateCodec : OutboundCodec<ChangeGameStateMessage>() {

    override fun encode(buf: ByteBuf, message: ChangeGameStateMessage): ByteBuf {
        buf.writeByte((message.type.toByte()).toInt())
        buf.writeFloat(message.value)

        return buf
    }
}

package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.outbound.ChangeGameStateMessage
import io.netty.buffer.ByteBuf
import io.netty.handler.codec.CodecException

class ChangeGameStateCodec : Codec<ChangeGameStateMessage> {

    override fun encode(buf: ByteBuf, message: ChangeGameStateMessage): ByteBuf {
        buf.writeByte((message.type.toByte()).toInt())
        buf.writeFloat(message.value)

        return buf
    }

    override fun decode(buffer: ByteBuf): ChangeGameStateMessage {
        throw CodecException("Decode operation not expected")
    }
}

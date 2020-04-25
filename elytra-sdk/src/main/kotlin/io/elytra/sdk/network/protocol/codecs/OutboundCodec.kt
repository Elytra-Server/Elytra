package io.elytra.sdk.network.protocol.codecs

import com.flowpowered.network.Codec
import com.flowpowered.network.Message
import io.netty.buffer.ByteBuf
import io.netty.handler.codec.DecoderException

abstract class OutboundCodec<M : Message> : Codec<M> {
    abstract override fun encode(buf: ByteBuf, message: M): ByteBuf

    override fun decode(buffer: ByteBuf): M {
        throw DecoderException("No decode for outbound codec")
    }
}

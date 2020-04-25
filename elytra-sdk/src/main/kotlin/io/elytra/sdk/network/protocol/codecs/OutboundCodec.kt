package io.elytra.sdk.network.protocol.codecs

import com.flowpowered.network.Codec
import com.flowpowered.network.Message
import io.netty.buffer.ByteBuf
import io.netty.handler.codec.DecoderException

interface OutboundCodec<M : Message> : Codec<M> {
    override fun decode(buffer: ByteBuf): M {
        throw DecoderException("No decode for outbound codec")
    }
}

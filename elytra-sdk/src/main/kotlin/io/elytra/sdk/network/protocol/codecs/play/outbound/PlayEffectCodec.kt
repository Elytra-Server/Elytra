package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.outbound.PlayEffectMessage
import io.netty.buffer.ByteBuf
import io.netty.handler.codec.DecoderException

class PlayEffectCodec : Codec<PlayEffectMessage> {

    override fun encode(buf: ByteBuf, message: PlayEffectMessage): ByteBuf {
        buf.writeInt(message.id)
        buf.writeInt(message.x)
        buf.writeInt(message.y)
        buf.writeInt(message.z)
        buf.writeInt(message.data)
        buf.writeBoolean(message.ignoreDistance)

        return buf
    }

    override fun decode(buffer: ByteBuf): PlayEffectMessage {
        throw DecoderException("Cannot decode PlayEffectMessage")
    }
}

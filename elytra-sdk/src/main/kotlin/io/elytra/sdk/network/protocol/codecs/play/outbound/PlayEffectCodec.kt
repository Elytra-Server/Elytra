package io.elytra.sdk.network.protocol.codecs.play.outbound

import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.PlayEffectMessage
import io.netty.buffer.ByteBuf

class PlayEffectCodec : OutboundCodec<PlayEffectMessage>() {

    override fun encode(buf: ByteBuf, message: PlayEffectMessage): ByteBuf {
        buf.writeInt(message.id)
        buf.writeInt(message.x)
        buf.writeInt(message.y)
        buf.writeInt(message.z)
        buf.writeInt(message.data)
        buf.writeBoolean(message.ignoreDistance)

        return buf
    }
}

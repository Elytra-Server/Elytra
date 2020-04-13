package io.elytra.sdk.network.protocol.codecs.play.outbound

import io.elytra.api.world.Position
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.PlayEffectMessage
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.ByteBuf

class PlayEffectCodec : OutboundCodec<PlayEffectMessage>() {

    override fun encode(buf: ByteBuf, message: PlayEffectMessage): ByteBuf {
        buf.writeInt(message.id)
        buf.minecraft.writePosition(Position(message.x.toDouble(), message.y.toDouble(), message.z.toDouble()))
        buf.writeByte(message.data)
        buf.writeBoolean(message.ignoreDistance)

        return buf
    }
}

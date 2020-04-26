package io.elytra.sdk.network.protocol.codecs.play.outbound

import io.elytra.api.world.Position
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.PlayEffectMessage
import io.elytra.sdk.network.utils.writePosition
import io.netty.buffer.ByteBuf

class PlayEffectCodec : OutboundCodec<PlayEffectMessage>() {
    override fun encode(buffer: ByteBuf, message: PlayEffectMessage): ByteBuf {
        buffer.writeInt(message.id)
        buffer.writePosition(Position(message.x.toDouble(), message.y.toDouble(), message.z.toDouble()))
        buffer.writeByte(message.data)
        buffer.writeBoolean(message.ignoreDistance)

        return buffer
    }
}

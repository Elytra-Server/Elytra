package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.UpdateHealthMessage
import io.netty.buffer.ByteBuf

class UpdateHealthCodec : OutboundCodec<UpdateHealthMessage> {
    override fun encode(buf: ByteBuf, message: UpdateHealthMessage): ByteBuf {
        buf.writeFloat(message.health)
        ByteBufUtils.writeVarInt(buf, message.food)
        buf.writeFloat(message.foodSaturation)

        return buf
    }
}

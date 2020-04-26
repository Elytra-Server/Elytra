package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.UpdateHealthMessage
import io.netty.buffer.ByteBuf

class UpdateHealthCodec : OutboundCodec<UpdateHealthMessage>() {
    override fun encode(buffer: ByteBuf, message: UpdateHealthMessage): ByteBuf {
        buffer.writeFloat(message.health)
        ByteBufUtils.writeVarInt(buffer, message.food)
        buffer.writeFloat(message.foodSaturation)

        return buffer
    }
}

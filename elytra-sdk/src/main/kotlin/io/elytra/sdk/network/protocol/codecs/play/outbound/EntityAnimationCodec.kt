package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.EntityAnimationMessage
import io.netty.buffer.ByteBuf

class EntityAnimationCodec : OutboundCodec<EntityAnimationMessage>() {
    override fun encode(buffer: ByteBuf, message: EntityAnimationMessage): ByteBuf {
        ByteBufUtils.writeVarInt(buffer, message.entityId)
        buffer.writeByte(message.animation.toByte().toInt())

        return buffer
    }
}

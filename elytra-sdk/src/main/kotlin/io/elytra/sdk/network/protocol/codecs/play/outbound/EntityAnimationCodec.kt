package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.EntityAnimationMessage
import io.netty.buffer.ByteBuf

class EntityAnimationCodec : OutboundCodec<EntityAnimationMessage> {
    override fun encode(buf: ByteBuf, message: EntityAnimationMessage): ByteBuf {
        ByteBufUtils.writeVarInt(buf, message.entityId)
        buf.writeByte(message.animation.toByte().toInt())

        return buf
    }
}

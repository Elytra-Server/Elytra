package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.message.play.outbound.EntityAnimationMessage
import io.netty.buffer.ByteBuf
import io.netty.handler.codec.CodecException

class EntityAnimationCodec : Codec<EntityAnimationMessage> {
    override fun encode(buf: ByteBuf, message: EntityAnimationMessage): ByteBuf {
        ByteBufUtils.writeVarInt(buf, message.entityId)
        buf.writeByte(message.animation.toByte().toInt())
        return buf
    }

    override fun decode(buffer: ByteBuf): EntityAnimationMessage {
        throw CodecException("There's no decode for EntityAnimationMessage")
    }
}

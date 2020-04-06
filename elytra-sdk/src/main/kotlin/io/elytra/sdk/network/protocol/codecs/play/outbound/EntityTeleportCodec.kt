package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.message.play.outbound.EntityTeleportMessage
import io.netty.buffer.ByteBuf
import java.io.IOException

class EntityTeleportCodec : Codec<EntityTeleportMessage> {
    override fun encode(buffer: ByteBuf, message: EntityTeleportMessage): ByteBuf {
        ByteBufUtils.writeVarInt(buffer, message.entityId)
        buffer.writeDouble(message.position.x)
        buffer.writeDouble(message.position.y)
        buffer.writeDouble(message.position.z)
        buffer.writeByte(0)
        buffer.writeByte(0)
        buffer.writeBoolean(message.ground)
        return buffer
    }

    override fun decode(buffer: ByteBuf): EntityTeleportMessage {
        throw IOException("No have decode support for this")
    }
}

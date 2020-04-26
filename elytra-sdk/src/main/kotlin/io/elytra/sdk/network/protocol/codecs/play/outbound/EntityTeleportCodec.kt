package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.EntityTeleportMessage
import io.netty.buffer.ByteBuf

class EntityTeleportCodec : OutboundCodec<EntityTeleportMessage>() {
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
}

package io.elytra.sdk.network.protocol.codecs.play.outbound

import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.EntityStatusMessage
import io.netty.buffer.ByteBuf

class EntityStatusCodec : OutboundCodec<EntityStatusMessage> {
    override fun encode(buffer: ByteBuf, message: EntityStatusMessage): ByteBuf {
        buffer.writeInt(message.entityId)
        buffer.writeByte(message.logicOpcode.toInt())

        return buffer
    }
}

package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.outbound.EntityStatusMessage
import io.netty.buffer.ByteBuf
import java.io.IOException

class EntityStatusCodec : Codec<EntityStatusMessage> {
    override fun encode(buffer: ByteBuf, message: EntityStatusMessage): ByteBuf {
        buffer.writeInt(message.entityId)
        buffer.writeByte(message.logicOpcode.toInt())
        return buffer
    }

    override fun decode(buffer: ByteBuf): EntityStatusMessage {
        throw IOException("No have decode support for this")
    }
}

package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.EntityStatusMessage
import io.netty.buffer.ByteBuf
import java.io.IOException

class EntityStatusCodec : Codec<EntityStatusMessage> {
	override fun encode(buf: ByteBuf, message: EntityStatusMessage): ByteBuf {
		buf.writeInt(message.entityId)
		buf.writeByte(message.logicOpcode.toInt())
		return buf
	}

	override fun decode(buffer: ByteBuf): EntityStatusMessage {
		throw IOException("No have decode support for this")
	}
}

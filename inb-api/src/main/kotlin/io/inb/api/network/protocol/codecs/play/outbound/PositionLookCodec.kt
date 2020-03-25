package io.inb.api.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.inb.api.network.protocol.message.play.PositionLookMessage
import io.netty.buffer.ByteBuf
import java.io.IOException

class PositionLookCodec : Codec<PositionLookMessage> {
	override fun encode(buf: ByteBuf, message: PositionLookMessage): ByteBuf {
		buf.writeDouble(message.x)
		buf.writeDouble(message.y)
		buf.writeDouble(message.z)
		buf.writeFloat(message.yaw)
		buf.writeFloat(message.pitch)

		return buf
	}

	override fun decode(buffer: ByteBuf): PositionLookMessage {
		throw IOException("No have decode support for this")
	}
}

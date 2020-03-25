package io.inb.api.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.inb.api.network.protocol.message.play.PositionLookMessage
import io.netty.buffer.ByteBuf

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
		val x = buffer.readDouble()
		val y = buffer.readDouble()
		val z = buffer.readDouble()
		val rotation = buffer.readFloat()
		val pitch = buffer.readFloat()

		return PositionLookMessage(x, y, z, rotation, pitch)
	}
}

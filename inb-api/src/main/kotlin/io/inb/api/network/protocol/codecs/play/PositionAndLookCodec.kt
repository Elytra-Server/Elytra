package io.inb.api.network.protocol.codecs.play

import com.flowpowered.network.Codec
import io.inb.api.network.protocol.message.play.PositionAndLookMessage
import io.netty.buffer.ByteBuf

class PositionAndLookCodec : Codec<PositionAndLookMessage> {
	override fun encode(buf: ByteBuf, positionAndLookMessage: PositionAndLookMessage): ByteBuf {
		buf.writeDouble(positionAndLookMessage.x)
		buf.writeDouble(positionAndLookMessage.y)
		buf.writeDouble(positionAndLookMessage.z)
		buf.writeFloat(positionAndLookMessage.yaw)
		buf.writeFloat(positionAndLookMessage.pitch)
		buf.writeBoolean(positionAndLookMessage.isGrounded)

		return buf
	}

	override fun decode(buffer: ByteBuf): PositionAndLookMessage {
		val x = buffer.readDouble()
		val y = buffer.readDouble()
		val z = buffer.readDouble()
		val yaw = buffer.readFloat()
		val pitch = buffer.readFloat()
		val isGrounded = buffer.readBoolean()

		return PositionAndLookMessage(x, y, z, yaw, pitch, isGrounded)
	}
}

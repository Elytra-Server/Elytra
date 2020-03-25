package io.inb.api.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.inb.api.network.protocol.message.play.PlayerPositionAndLookMessage
import io.netty.buffer.ByteBuf

class PlayerPositionAndLookCodec : Codec<PlayerPositionAndLookMessage> {
	override fun encode(buf: ByteBuf, playerPositionAndLookMessage: PlayerPositionAndLookMessage): ByteBuf {
		buf.writeDouble(playerPositionAndLookMessage.x)
		buf.writeDouble(playerPositionAndLookMessage.y)
		buf.writeDouble(playerPositionAndLookMessage.z)
		buf.writeFloat(playerPositionAndLookMessage.yaw)
		buf.writeFloat(playerPositionAndLookMessage.pitch)
		buf.writeBoolean(playerPositionAndLookMessage.isGrounded)

		return buf
	}

	override fun decode(buffer: ByteBuf): PlayerPositionAndLookMessage {
		val x = buffer.readDouble()
		val y = buffer.readDouble()
		val z = buffer.readDouble()
		val yaw = buffer.readFloat()
		val pitch = buffer.readFloat()
		val isGrounded = buffer.readBoolean()

		return PlayerPositionAndLookMessage(x, y, z, yaw, pitch, isGrounded)
	}
}

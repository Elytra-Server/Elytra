package io.inb.api.network.protocol.codecs.play

import com.flowpowered.network.Codec
import io.inb.api.network.protocol.message.play.PlayerPositionMessage
import io.netty.buffer.ByteBuf
import java.io.IOException

class PlayerPositionCodec : Codec<PlayerPositionMessage> {

	override fun decode(buffer: ByteBuf): PlayerPositionMessage? {
		val x = buffer.readDouble()
		val y = buffer.readDouble()
		val z = buffer.readDouble()
		val isGrounded = buffer.readBoolean()

		return PlayerPositionMessage(x, y, z, isGrounded)
	}

	@Throws(IOException::class)
	override fun encode(buf: ByteBuf, message: PlayerPositionMessage): ByteBuf? {
		buf.writeDouble(message.x)
		buf.writeDouble(message.y)
		buf.writeDouble(message.z)
		buf.writeBoolean(message.isGrounded)

		return buf
	}
}

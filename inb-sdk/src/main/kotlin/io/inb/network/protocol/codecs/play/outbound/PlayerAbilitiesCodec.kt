package io.inb.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.inb.network.protocol.message.play.PlayerAbilitiesMessage
import io.netty.buffer.ByteBuf
import java.io.IOException
import kotlin.experimental.or

class PlayerAbilitiesCodec : Codec<PlayerAbilitiesMessage> {
	override fun encode(buf: ByteBuf, message: PlayerAbilitiesMessage): ByteBuf {
		var b0: Byte = 0

		if (message.invulnerable) {
			b0 = (b0 or 1) as Byte
		}

		if (message.flying) {
			b0 = (b0 or 2) as Byte
		}

		if (message.allowFlying) {
			b0 = (b0 or 4) as Byte
		}

		if (message.creativeMode) {
			b0 = (b0 or 8) as Byte
		}

		buf.writeByte(b0.toInt())
		buf.writeFloat(message.flySpeed)
		buf.writeFloat(message.walkSpeed)
		return buf
	}

	override fun decode(buffer: ByteBuf): PlayerAbilitiesMessage {
		throw IOException("No have decode support for this")
	}
}

package io.inb.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.inb.network.protocol.message.play.HeldItemChangeMessage
import io.netty.buffer.ByteBuf
import java.io.IOException

class HeldItemChangeCodec : Codec<HeldItemChangeMessage> {
	override fun encode(buf: ByteBuf, message: HeldItemChangeMessage): ByteBuf {
		buf.writeByte(message.heldItemHotbarIndex)

		println("held item - " + message.heldItemHotbarIndex)
		return buf
	}

	override fun decode(buffer: ByteBuf): HeldItemChangeMessage {
		throw IOException("No have decode support for this")
	}
}

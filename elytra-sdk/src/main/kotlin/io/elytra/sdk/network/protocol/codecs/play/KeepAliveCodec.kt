package io.elytra.sdk.network.protocol.codecs.play

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.KeepAliveMessage
import io.netty.buffer.ByteBuf

class KeepAliveCodec : Codec<KeepAliveMessage>{
	override fun encode(buf: ByteBuf, message: KeepAliveMessage): ByteBuf {
		buf.writeLong(message.id)
		return buf
	}

	override fun decode(buffer: ByteBuf): KeepAliveMessage {
		val id = buffer.readLong()
		return KeepAliveMessage(id)
	}
}

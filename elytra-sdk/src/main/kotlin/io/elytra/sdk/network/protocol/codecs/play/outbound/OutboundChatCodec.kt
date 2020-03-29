package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.message.play.OutboundChatMessage
import io.netty.buffer.ByteBuf

class OutboundChatCodec : Codec<OutboundChatMessage> {
	override fun encode(buf: ByteBuf, message: OutboundChatMessage): ByteBuf {
		ByteBufUtils.writeUTF8(buf, message.content)
		buf.writeByte(message.mode)

		return buf
	}

	override fun decode(buffer: ByteBuf): OutboundChatMessage {
		val content = ByteBufUtils.readUTF8(buffer)
		val mode = buffer.readByte()

		return OutboundChatMessage(content, mode.toInt())
	}
}

package io.inb.api.network.protocol.codecs.play

import com.flowpowered.network.Codec
import io.inb.api.network.protocol.message.play.CustomPayloadMessage
import io.inb.api.utils.Utils
import io.netty.buffer.ByteBuf
import java.io.IOException

class CustomPayloadCodec : Codec<CustomPayloadMessage> {
	override fun encode(buf: ByteBuf, message: CustomPayloadMessage): ByteBuf {
		Utils.writeString(buf,message.channel)
		buf.writeBytes(message.data)
		return buf
	}

	override fun decode(buffer: ByteBuf): CustomPayloadMessage {
		val channel = Utils.readStringFromBuffer(buffer,20)
		val i = buffer.readableBytes()
		if (i in 0..32767) {
			val data = buffer.readBytes(i)
			return CustomPayloadMessage(channel,data)
		} else {
			throw IOException("Payload may not be larger than 32767 bytes")
		}
	}
}

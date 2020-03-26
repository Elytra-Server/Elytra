package io.inb.network.protocol.codecs

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.inb.network.protocol.message.DisconnectMessage
import io.netty.buffer.ByteBuf

class DisconnectCodec : Codec<DisconnectMessage> {
	override fun encode(buf: ByteBuf, message: DisconnectMessage): ByteBuf {
		ByteBufUtils.writeUTF8(buf, message.message)
		return buf
	}

	override fun decode(buffer: ByteBuf?): DisconnectMessage {
		return DisconnectMessage(ByteBufUtils.readUTF8(buffer))
	}
}

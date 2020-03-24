package io.inb.api.network.protocol.codecs.status

import com.flowpowered.network.Codec
import io.inb.api.network.protocol.message.status.StatusRequestMessage
import io.netty.buffer.ByteBuf

class StatusRequestCodec : Codec<StatusRequestMessage> {
	override fun encode(buf: ByteBuf, message: StatusRequestMessage): ByteBuf {
		return buf;
	}

	override fun decode(buffer: ByteBuf): StatusRequestMessage {
		return StatusRequestMessage();
	}
}

package io.inb.api.network.protocol.codecs.status

import com.flowpowered.network.Codec
import io.inb.api.network.protocol.message.status.StatusPingMessage
import io.netty.buffer.ByteBuf

class StatusPingCodec : Codec<StatusPingMessage> {
	override fun encode(buf: ByteBuf, message: StatusPingMessage): ByteBuf {
		buf.writeLong(message.time);
		return buf;
	}

	override fun decode(buffer: ByteBuf): StatusPingMessage {
		return StatusPingMessage(buffer.readLong());
	}
}

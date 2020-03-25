package io.inb.api.network.protocol.codecs.status

import com.flowpowered.network.Codec
import io.inb.api.network.protocol.message.status.PingMessage
import io.netty.buffer.ByteBuf

class PingCodec : Codec<PingMessage> {
	override fun encode(buf: ByteBuf, message: PingMessage): ByteBuf {
		buf.writeLong(message.time);
		return buf;
	}

	override fun decode(buffer: ByteBuf): PingMessage {
		return PingMessage(buffer.readLong());
	}
}

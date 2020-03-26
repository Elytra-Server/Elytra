package io.inb.network.protocol.codecs.status

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.inb.network.protocol.message.status.ServerInfoMessage
import io.netty.buffer.ByteBuf

class ServerInfoCodec : Codec<ServerInfoMessage> {
	override fun decode(buffer: ByteBuf): ServerInfoMessage {
		val json = ByteBufUtils.readUTF8(buffer)
		return ServerInfoMessage(json);
	}

	override fun encode(buf: ByteBuf, message: ServerInfoMessage): ByteBuf {
		ByteBufUtils.writeUTF8(buf, message.body);
		return buf;
	}
}

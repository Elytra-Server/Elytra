package io.inb.api.network.protocol.codecs

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.inb.api.network.protocol.message.LoginStartMessage
import io.netty.buffer.ByteBuf

class LoginCodec : Codec<LoginStartMessage> {
	override fun encode(buf: ByteBuf, message: LoginStartMessage): ByteBuf {
		ByteBufUtils.writeUTF8(buf, message.username);
		return buf
	}

	override fun decode(buffer: ByteBuf): LoginStartMessage {
		return LoginStartMessage(ByteBufUtils.readUTF8(buffer))
	}
}

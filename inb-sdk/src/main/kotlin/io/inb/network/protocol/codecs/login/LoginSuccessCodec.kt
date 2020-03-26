package io.inb.network.protocol.codecs.login

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.inb.network.protocol.message.login.LoginSuccessMessage
import io.netty.buffer.ByteBuf

class LoginSuccessCodec : Codec<LoginSuccessMessage> {

	override fun decode(buffer: ByteBuf): LoginSuccessMessage {
		val uuid = ByteBufUtils.readUTF8(buffer)
		val username = ByteBufUtils.readUTF8(buffer)

		return LoginSuccessMessage(uuid, username)
	}

	override fun encode(buf: ByteBuf, message: LoginSuccessMessage): ByteBuf {
		ByteBufUtils.writeUTF8(buf, message.uuid)
		ByteBufUtils.writeUTF8(buf, message.username)

		return buf
	}
}

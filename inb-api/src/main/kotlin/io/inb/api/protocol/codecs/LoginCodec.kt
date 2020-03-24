package io.inb.api.protocol.codecs

import io.inb.api.protocol.Codec
import io.inb.api.protocol.packets.LoginPacket
import io.inb.api.utils.Utils
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import kotlin.reflect.KClass

class LoginCodec(
	override var type: KClass<LoginPacket> = LoginPacket::class,
	override var id: Int = 0x02
) : Codec<LoginPacket> {

	override fun encode(packet: LoginPacket): ByteBuf {
		val buffer: ByteBuf = Unpooled.buffer()
		buffer.writeInt(packet.id)
		Utils.writeString(buffer, packet.username)

		return buffer
	}

	override fun decode(buffer: ByteBuf): LoginPacket {
		val id = buffer.readInt()
		val username = Utils.readString(buffer)

		return LoginPacket(username, id)
	}
}

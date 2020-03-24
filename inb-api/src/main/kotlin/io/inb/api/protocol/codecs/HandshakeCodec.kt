package io.inb.api.protocol.codecs

import io.inb.api.protocol.Codec
import io.inb.api.utils.Utils
import io.inb.api.protocol.packets.HandshakePacket
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import kotlin.reflect.KClass

class HandshakeCodec(
	override var type: KClass<HandshakePacket> = HandshakePacket::class,
	override var id: Short = 0x00
) : Codec<HandshakePacket> {
	override fun encode(packet: HandshakePacket): ByteBuf {
		val buffer: ByteBuf = Unpooled.buffer()
		Utils.writeString(buffer, packet.username)

		return buffer
	}

	override fun decode(buffer: ByteBuf): HandshakePacket {
		val username = Utils.readString(buffer)

		return HandshakePacket(username)
	}
}

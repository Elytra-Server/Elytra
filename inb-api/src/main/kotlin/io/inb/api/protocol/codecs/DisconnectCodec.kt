package io.inb.api.protocol.codecs

import io.inb.api.protocol.Codec
import io.inb.api.protocol.packets.DisconnectPacket
import io.inb.api.utils.Utils
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import kotlin.reflect.KClass

class DisconnectCodec(
	override var type: KClass<DisconnectPacket> = DisconnectPacket::class,
	override var id: Int = 0xFF
) : Codec<DisconnectPacket> {

	override fun encode(packet: DisconnectPacket): ByteBuf {
		val buffer: ByteBuf = Unpooled.buffer()
		Utils.writeString(buffer, packet.message)

		return buffer
	}

	override fun decode(buffer: ByteBuf): DisconnectPacket = DisconnectPacket(Utils.readString(buffer))
}

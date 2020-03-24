package io.inb.api.network.protocol.codecs

import io.inb.api.network.protocol.message.DisconnectMessage
import io.inb.api.utils.Utils
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import kotlin.reflect.KClass

class DisconnectCodec(
	override var type: KClass<DisconnectMessage> = DisconnectMessage::class,
	override var id: Int = 0xFF
) : Codec<DisconnectMessage> {

	override fun encode(message: DisconnectMessage): ByteBuf {
		val buffer: ByteBuf = Unpooled.buffer()
		Utils.writeString(buffer, message.message)

		return buffer
	}

	override fun decode(buffer: ByteBuf): DisconnectMessage = DisconnectMessage(Utils.readString(buffer))
}

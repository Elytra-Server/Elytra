package io.inb.api.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.inb.api.network.protocol.message.play.PlayerPosLookMessage
import io.inb.api.network.utils.ByteUtils
import io.netty.buffer.ByteBuf
import java.io.IOException

class PlayerPosLookCodec : Codec<PlayerPosLookMessage> {
	override fun encode(buf: ByteBuf, message: PlayerPosLookMessage): ByteBuf {
		buf.writeDouble(message.x)
		buf.writeDouble(message.y)
		buf.writeDouble(message.z)
		buf.writeFloat(message.yaw)
		buf.writeFloat(message.pitch)
		buf.writeByte(message.flags.toInt())
		ByteUtils.writeVarIntToBuffer(buf,message.teleportId)

		println(message)
		return buf
	}

	override fun decode(buffer: ByteBuf): PlayerPosLookMessage {
		throw IOException("No have decode support for this")
	}
}

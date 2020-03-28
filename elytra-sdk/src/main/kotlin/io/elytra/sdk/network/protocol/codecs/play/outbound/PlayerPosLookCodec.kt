package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.PlayerPosLookMessage
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.ByteBuf
import java.io.IOException

class PlayerPosLookCodec : Codec<PlayerPosLookMessage> {

	override fun encode(buffer: ByteBuf, message: PlayerPosLookMessage): ByteBuf {
		buffer.writeDouble(message.x)
		buffer.writeDouble(message.y)
		buffer.writeDouble(message.z)
		buffer.writeFloat(message.yaw)
		buffer.writeFloat(message.pitch)
		buffer.writeByte(message.flags.toInt())
		buffer.minecraft.writeVarInt(message.teleportId)

		println(message)
		return buffer
	}

	override fun decode(buffer: ByteBuf): PlayerPosLookMessage {
		throw IOException("No have decode support for this")
	}
}

package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.JoinGameMessage
import io.elytra.sdk.network.utils.MinecraftByteBuf
import io.netty.buffer.ByteBuf
import java.io.IOException

class JoinGameCodec : Codec<JoinGameMessage> {

	private val ByteBuf.minecraft get() = MinecraftByteBuf(this)

	override fun encode(buffer: ByteBuf, message: JoinGameMessage): ByteBuf {
		buffer.writeInt(message.id)
		buffer.minecraft.writeEnumValue(message.gameMode)
		buffer.writeInt(message.dimension)
		buffer.minecraft.writeEnumValue(message.difficulty)
		buffer.writeByte(message.maxPlayers)
		buffer.minecraft.writeString(message.worldType)
		buffer.writeBoolean(message.reducedDebugInfo)
		return buffer
	}

	override fun decode(buffer: ByteBuf): JoinGameMessage {
		throw IOException("No have decode support for this")
	}

}

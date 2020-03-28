package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.ServerDifficultyMessage
import io.netty.buffer.ByteBuf
import java.io.IOException

class ServerDifficultyCodec : Codec<ServerDifficultyMessage> {
	override fun encode(buffer: ByteBuf, message: ServerDifficultyMessage): ByteBuf {
		buffer.writeByte(message.difficulty.value)
		return buffer
	}

	override fun decode(buffer: ByteBuf): ServerDifficultyMessage {
		throw IOException("No have decode support for this")
	}
}

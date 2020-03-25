package io.inb.api.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.inb.api.network.protocol.message.play.EnumDifficulty
import io.inb.api.network.protocol.message.play.ServerDifficultyMessage
import io.netty.buffer.ByteBuf
import java.io.IOException

class ServerDifficultyCodec : Codec<ServerDifficultyMessage> {
	override fun encode(buf: ByteBuf, message: ServerDifficultyMessage): ByteBuf {
		buf.writeByte(message.difficulty.value)
		return buf
	}

	override fun decode(buffer: ByteBuf): ServerDifficultyMessage {
		throw IOException("No have decode support for this")
	}
}

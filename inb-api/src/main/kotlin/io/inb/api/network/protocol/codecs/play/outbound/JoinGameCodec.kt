package io.inb.api.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.inb.api.network.protocol.message.play.JoinGameMessage
import io.inb.api.utils.Utils
import io.netty.buffer.ByteBuf
import java.io.IOException

class JoinGameCodec : Codec<JoinGameMessage> {
	override fun encode(buf: ByteBuf, message: JoinGameMessage): ByteBuf {
		buf.writeInt(message.id)
		buf.writeByte(message.gameMode)
		buf.writeInt(message.dimension)
		buf.writeByte(message.difficulty)
		buf.writeByte(message.maxPlayers)
		Utils.writeString(buf, message.worldType)
		buf.writeBoolean(message.reducedDebugInfo)

		return buf
	}

	override fun decode(buffer: ByteBuf): JoinGameMessage {
		throw IOException("No have decode support for this")
	}

}

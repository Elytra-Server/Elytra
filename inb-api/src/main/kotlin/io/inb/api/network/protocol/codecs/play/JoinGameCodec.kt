package io.inb.api.network.protocol.codecs.play

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.inb.api.network.protocol.message.play.JoinGameMessage
import io.netty.buffer.ByteBuf

class JoinGameCodec : Codec<JoinGameMessage> {
	override fun encode(buf: ByteBuf, message: JoinGameMessage): ByteBuf {
		buf.writeInt(message.id)
		buf.writeByte(message.gameMode)
		buf.writeInt(message.dimension)
		buf.writeByte(message.difficulty)
		buf.writeByte(message.maxPlayers)
		ByteBufUtils.writeUTF8(buf, message.levelType)

		return buf
	}

	override fun decode(buffer: ByteBuf): JoinGameMessage {
		val id = buffer.readInt()
		val gameMode = buffer.readByte()
		val dimension = buffer.readInt()
		val difficulty = buffer.readByte()
		val maxPlayers = buffer.readByte()
		val levelType = ByteBufUtils.readUTF8(buffer)

		return JoinGameMessage(
			id,
			gameMode.toInt(),
			dimension,
			difficulty.toInt(),
			maxPlayers.toInt(),
			levelType
		)
	}

}

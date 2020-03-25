package io.inb.api.network.protocol.codecs.play

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.inb.api.network.protocol.message.play.JoinGameMessage
import io.netty.buffer.ByteBuf

class JoinGameCodec : Codec<JoinGameMessage> {
	override fun encode(buf: ByteBuf, message: JoinGameMessage): ByteBuf {
		buf.writeInt(message.id)
		buf.writeInt(message.gameMode)
		buf.writeByte(message.maxPlayers)
		ByteBufUtils.writeUTF8(buf, message.worldType)
		buf.writeInt(message.viewDistance)
		buf.writeBoolean(message.reducedDebugInfo)
		buf.writeBoolean(message.enableRespawnScreen)

		return buf
	}

	override fun decode(buffer: ByteBuf): JoinGameMessage {
		val id = buffer.readInt()
		val gameMode = buffer.readByte()
		val maxPlayers = buffer.readByte()
		val worldType = ByteBufUtils.readUTF8(buffer)
		val viewDistance = buffer.readInt()
		val enableRespawnScreen = buffer.readBoolean()
		val reducedDebugInfo = buffer.readBoolean()

		return JoinGameMessage(
			id,
			gameMode.toInt(),
			maxPlayers.toInt(),
			worldType,
			viewDistance,
			reducedDebugInfo,
			enableRespawnScreen
		)
	}

}

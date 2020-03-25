package io.inb.api.network.protocol.packets

import io.inb.api.network.protocol.codecs.play.JoinGameCodec
import io.inb.api.network.protocol.codecs.play.PlayerPositionCodec
import io.inb.api.network.protocol.codecs.play.PositionAndLookCodec
import io.inb.api.network.protocol.codecs.status.StatusRequestCodec
import io.inb.api.network.protocol.handlers.play.PlayerPositionHandler
import io.inb.api.network.protocol.handlers.play.PositionAndLookHandler
import io.inb.api.network.protocol.handlers.status.StatusRequestHandler
import io.inb.api.network.protocol.message.play.JoinGameMessage
import io.inb.api.network.protocol.message.play.PlayerPositionMessage
import io.inb.api.network.protocol.message.play.PositionAndLookMessage
import io.inb.api.network.protocol.message.status.StatusRequestMessage

/**
 * @param [opcode] in this case means whats the highest packet id on the play packets
 */
class PlayPacket : BasicPacket("PLAY", 0x4F) {

	init {
		inbound(0x0D,
			PlayerPositionMessage::class.java,
			PlayerPositionCodec::class.java,
			PlayerPositionHandler::class.java
		)

		inbound(0x0E, PositionAndLookMessage::class.java, PositionAndLookCodec::class.java, PositionAndLookHandler::class.java)

		outbound(0x23, JoinGameMessage::class.java, JoinGameCodec::class.java)
		outbound(0x2F, PositionAndLookMessage::class.java, PositionAndLookCodec::class.java)
	}
}

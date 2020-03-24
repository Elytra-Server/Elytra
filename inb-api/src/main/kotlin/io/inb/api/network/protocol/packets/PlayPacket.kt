package io.inb.api.network.protocol.packets

import io.inb.api.network.protocol.codecs.play.JoinGameCodec
import io.inb.api.network.protocol.message.play.JoinGameMessage

/**
 * @param [opcode] in this case means whats the highest packet id on the play packets
 */
class PlayPacket : BasicPacket("PLAY", 0x4F) {

	init {
		outbound(0x23, JoinGameMessage::class.java, JoinGameCodec::class.java)
	}
}

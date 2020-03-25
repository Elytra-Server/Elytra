package io.inb.api.network.protocol.packets

import io.inb.api.network.protocol.codecs.play.ClientSettingsCodec
import io.inb.api.network.protocol.codecs.play.ConfirmTeleportCodec
import io.inb.api.network.protocol.codecs.play.JoinGameCodec
import io.inb.api.network.protocol.handlers.play.ClientSettingsHandler
import io.inb.api.network.protocol.handlers.play.ConfirmTeleportHandle
import io.inb.api.network.protocol.message.play.ClientSettingsMessage
import io.inb.api.network.protocol.message.play.ConfirmTeleportMessage
import io.inb.api.network.protocol.message.play.JoinGameMessage

/**
 * @param [opcode] in this case means whats the highest packet id on the play packets
 */
class PlayPacket : BasicPacket("PLAY", 0x4F) {

	init {

		inbound(
			0x00,
			ConfirmTeleportMessage::class.java,
			ConfirmTeleportCodec::class.java,
			ConfirmTeleportHandle::class.java
		)

		inbound(
			0x05,
			ClientSettingsMessage::class.java,
			ClientSettingsCodec::class.java,
			ClientSettingsHandler::class.java
		)

		outbound(0x23, JoinGameMessage::class.java, JoinGameCodec::class.java)
	}
}

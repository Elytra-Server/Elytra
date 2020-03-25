package io.inb.api.network.protocol.packets

import io.inb.api.network.protocol.codecs.play.inbound.ClientSettingsCodec
import io.inb.api.network.protocol.codecs.play.inbound.ConfirmTeleportCodec
import io.inb.api.network.protocol.codecs.play.outbound.JoinGameCodec
import io.inb.api.network.protocol.handlers.play.ClientSettingsHandler
import io.inb.api.network.protocol.handlers.play.ConfirmTeleportHandle
import io.inb.api.network.protocol.codecs.play.inbound.PlayerPositionCodec
import io.inb.api.network.protocol.codecs.play.inbound.PlayerPositionAndLookCodec
import io.inb.api.network.protocol.codecs.play.outbound.PositionLookCodec
import io.inb.api.network.protocol.handlers.play.PlayerPositionHandler
import io.inb.api.network.protocol.handlers.play.PositionAndLookHandler
import io.inb.api.network.protocol.message.play.*

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

		inbound(0x0E,
			PlayerPositionAndLookMessage::class.java,
			PlayerPositionAndLookCodec::class.java,
			PositionAndLookHandler::class.java
		)

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
		outbound(0x2F, PositionLookMessage::class.java, PositionLookCodec::class.java)
	}
}

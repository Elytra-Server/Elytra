package io.inb.api.network.protocol.packets

import io.inb.api.network.protocol.codecs.play.CustomPayloadCodec
import io.inb.api.network.protocol.codecs.play.inbound.*
import io.inb.api.network.protocol.codecs.play.outbound.*
import io.inb.api.network.protocol.handlers.play.*
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
			ConfirmTeleportHandler::class.java
		)

		inbound(
			0x05,
			ClientSettingsMessage::class.java,
			ClientSettingsCodec::class.java,
			ClientSettingsHandler::class.java
		)

		inbound(
			0x19,
			CustomPayloadMessage::class.java,
			CustomPayloadCodec::class.java,
			CustomPayloadHandler::class.java
		)

		inbound(
			0x03,
			ChatMessage::class.java,
			ChatCodec::class.java,
			ChatHandler::class.java
		)

		outbound(0x40, HeldItemChangeMessage::class.java, HeldItemChangeCodec::class.java)
		outbound(0x32, PlayerAbilitiesMessage::class.java, PlayerAbilitiesCodec::class.java)
		outbound(0x0E, ServerDifficultyMessage::class.java, ServerDifficultyCodec::class.java)
		outbound(0x19, CustomPayloadMessage::class.java, CustomPayloadCodec::class.java)
		outbound(0x23, JoinGameMessage::class.java, JoinGameCodec::class.java)
		outbound(0x2F, PositionLookMessage::class.java, PositionLookCodec::class.java)
	}
}

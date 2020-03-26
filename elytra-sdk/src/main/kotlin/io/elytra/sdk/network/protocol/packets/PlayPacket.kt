package io.elytra.sdk.network.protocol.packets

import io.elytra.sdk.network.protocol.codecs.play.CustomPayloadCodec
import io.elytra.sdk.network.protocol.codecs.play.inbound.ChatCodec
import io.elytra.sdk.network.protocol.codecs.play.inbound.ClientSettingsCodec
import io.elytra.sdk.network.protocol.codecs.play.inbound.ConfirmTeleportCodec
import io.elytra.sdk.network.protocol.codecs.play.outbound.*
import io.elytra.sdk.network.protocol.handlers.play.ChatHandler
import io.elytra.sdk.network.protocol.handlers.play.ClientSettingsHandler
import io.elytra.sdk.network.protocol.handlers.play.ConfirmTeleportHandler
import io.elytra.sdk.network.protocol.handlers.play.CustomPayloadHandler
import io.elytra.sdk.network.protocol.message.play.*
import io.inb.network.protocol.codecs.play.inbound.*
import io.inb.network.protocol.codecs.play.outbound.*
import io.inb.network.protocol.handlers.play.*
import io.inb.network.protocol.message.play.*

/**
 * @param [opcode] in this case means whats the highest packet id on the play packets
 */
class PlayPacket : BasicPacket("PLAY", 0x4F) {

	init {
		inbound(
			0x00,
			ConfirmTeleportMessage::class.java,
			ConfirmTeleportCodec::class.java,
			ConfirmTeleportHandler::class.java
		)

		inbound(
			0x04,
			ClientSettingsMessage::class.java,
			ClientSettingsCodec::class.java,
			ClientSettingsHandler::class.java
		)

		inbound(
			0x9,
			CustomPayloadMessage::class.java,
			CustomPayloadCodec::class.java,
			CustomPayloadHandler::class.java
		)

		inbound(
			0x02,
			ChatMessage::class.java,
			ChatCodec::class.java,
			ChatHandler::class.java
		)

		outbound(0x39, HeldItemChangeMessage::class.java, HeldItemChangeCodec::class.java)
		outbound(0x2B, PlayerAbilitiesMessage::class.java, PlayerAbilitiesCodec::class.java)
		outbound(0x0D, ServerDifficultyMessage::class.java, ServerDifficultyCodec::class.java)
		outbound(0x18, CustomPayloadMessage::class.java, CustomPayloadCodec::class.java)
		outbound(0x23, JoinGameMessage::class.java, JoinGameCodec::class.java)
		outbound(0x2F, PlayerPosLookMessage::class.java, PlayerPosLookCodec::class.java)
		outbound(0x1B, EntityStatusMessage::class.java, EntityStatusCodec::class.java)
	}
}

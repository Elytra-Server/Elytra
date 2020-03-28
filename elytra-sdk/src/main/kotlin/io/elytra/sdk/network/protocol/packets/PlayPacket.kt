package io.elytra.sdk.network.protocol.packets

import io.elytra.sdk.network.protocol.ProtocolInfo
import io.elytra.sdk.network.protocol.codecs.play.CustomPayloadCodec
import io.elytra.sdk.network.protocol.codecs.play.inbound.ChatCodec
import io.elytra.sdk.network.protocol.codecs.play.inbound.ChunkDataCodec
import io.elytra.sdk.network.protocol.codecs.play.inbound.ClientSettingsCodec
import io.elytra.sdk.network.protocol.codecs.play.inbound.ConfirmTeleportCodec
import io.elytra.sdk.network.protocol.codecs.play.outbound.*
import io.elytra.sdk.network.protocol.handlers.play.ChatHandler
import io.elytra.sdk.network.protocol.handlers.play.ClientSettingsHandler
import io.elytra.sdk.network.protocol.handlers.play.ConfirmTeleportHandler
import io.elytra.sdk.network.protocol.handlers.play.CustomPayloadHandler
import io.elytra.sdk.network.protocol.message.play.*

/**
 * @param [opcode] in this case means whats the highest packet id on the play packets
 */
class PlayPacket : BasicPacket("PLAY", 0x4F) {

	init {
		inbound(
			ProtocolInfo.CONFIRM_TELEPORT,
			ConfirmTeleportMessage::class.java,
			ConfirmTeleportCodec::class.java,
			ConfirmTeleportHandler::class.java
		)

		inbound(
			ProtocolInfo.CLIENT_SETTINGS,
			ClientSettingsMessage::class.java,
			ClientSettingsCodec::class.java,
			ClientSettingsHandler::class.java
		)

		inbound(
			ProtocolInfo.I_CUSTOM_PAYLOAD,
			CustomPayloadMessage::class.java,
			CustomPayloadCodec::class.java,
			CustomPayloadHandler::class.java
		)

		inbound(
			ProtocolInfo.I_CHAT,
			ChatMessage::class.java,
			ChatCodec::class.java,
			ChatHandler::class.java
		)

		outbound(ProtocolInfo.HELD_ITEM_CHANGE, HeldItemChangeMessage::class.java, HeldItemChangeCodec::class.java)//Maybe 0x39 // 0x3A
		outbound(ProtocolInfo.PLAYER_ABILITIES, PlayerAbilitiesMessage::class.java, PlayerAbilitiesCodec::class.java)
		outbound(ProtocolInfo.SERVER_DIFFICULTY, ServerDifficultyMessage::class.java, ServerDifficultyCodec::class.java)
		outbound(ProtocolInfo.O_CUSTOM_PAYLOAD, CustomPayloadMessage::class.java, CustomPayloadCodec::class.java)
		outbound(ProtocolInfo.JOIN_GAME, JoinGameMessage::class.java, JoinGameCodec::class.java)
		outbound(ProtocolInfo.PLAYER_POS_LOOK, PlayerPosLookMessage::class.java, PlayerPosLookCodec::class.java)
		outbound(ProtocolInfo.ENTITY_STATUS, EntityStatusMessage::class.java, EntityStatusCodec::class.java)
		outbound(ProtocolInfo.LOAD_CHUNK_DATA, ChunkDataMessage::class.java, ChunkDataCodec::class.java)
	}
}

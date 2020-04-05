package io.elytra.sdk.network.protocol.packets

import io.elytra.sdk.network.protocol.ProtocolInfo
import io.elytra.sdk.network.protocol.codecs.DisconnectCodec
import io.elytra.sdk.network.protocol.codecs.play.CustomPayloadCodec
import io.elytra.sdk.network.protocol.codecs.play.KeepAliveCodec
import io.elytra.sdk.network.protocol.codecs.play.inbound.*
import io.elytra.sdk.network.protocol.codecs.play.outbound.*
import io.elytra.sdk.network.protocol.handlers.play.*
import io.elytra.sdk.network.protocol.message.DisconnectMessage
import io.elytra.sdk.network.protocol.message.play.*

/**
 * @param [opcode] in this case means whats the highest packet id on the play packets
 */
class PlayPacket : BasicPacket("PLAY", 0x96) {

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

        inbound(
            ProtocolInfo.I_KEEP_ALIVE,
            KeepAliveMessage::class.java,
            KeepAliveCodec::class.java,
            KeepAliveHandler::class.java
        )

        inbound(ProtocolInfo.I_PLAYER_UPDATE,
            PlayerUpdateMessage::class.java,
            PlayerUpdateCodec::class.java,
            PlayerUpdateHandler::class.java
        )

        outbound(ProtocolInfo.HELD_ITEM_CHANGE, HeldItemChangeMessage::class.java, HeldItemChangeCodec::class.java) // Maybe 0x39 // 0x3A
        outbound(ProtocolInfo.PLAYER_ABILITIES, PlayerAbilitiesMessage::class.java, PlayerAbilitiesCodec::class.java)
        outbound(ProtocolInfo.SERVER_DIFFICULTY, ServerDifficultyMessage::class.java, ServerDifficultyCodec::class.java)
        outbound(ProtocolInfo.O_CUSTOM_PAYLOAD, CustomPayloadMessage::class.java, CustomPayloadCodec::class.java)
        outbound(ProtocolInfo.JOIN_GAME, JoinGameMessage::class.java, JoinGameCodec::class.java)
        outbound(ProtocolInfo.PLAYER_POS_LOOK, PlayerRotationMessage::class.java, PlayerRotationCodec::class.java)
        outbound(ProtocolInfo.ENTITY_STATUS, EntityStatusMessage::class.java, EntityStatusCodec::class.java)
        outbound(ProtocolInfo.PLAY_DISCONNECT, DisconnectMessage::class.java, DisconnectCodec::class.java)
        // outbound(ProtocolInfo.LOAD_CHUNK_DATA, ChunkDataMessage::class.java, ChunkDataCodec::class.java)
        outbound(ProtocolInfo.O_CHAT, OutboundChatMessage::class.java, OutboundChatCodec::class.java)
        outbound(ProtocolInfo.PLAYER_LIST_ITEM, PlayerListItemMessage::class.java, PlayerListItemCodec::class.java)
        outbound(ProtocolInfo.O_KEEP_ALIVE, KeepAliveMessage::class.java, KeepAliveCodec::class.java)
        outbound(ProtocolInfo.BOSS_INFO, BossInfoMessage::class.java, BossInfoCodec::class.java)
        outbound(ProtocolInfo.TITLE, TitleMessage::class.java, TitleCodec::class.java)
        outbound(ProtocolInfo.O_SPAWNPLAYER, SpawnPlayerMessage::class.java, SpawnPlayerCodec::class.java)
    }
}

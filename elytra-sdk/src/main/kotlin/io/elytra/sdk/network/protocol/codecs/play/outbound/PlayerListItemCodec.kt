package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.message.play.outbound.Action
import io.elytra.sdk.network.protocol.message.play.outbound.PlayerListItemMessage
import io.elytra.sdk.network.utils.minecraft
import io.elytra.sdk.utils.asJson
import io.netty.buffer.ByteBuf
import java.io.IOException

class PlayerListItemCodec : Codec<PlayerListItemMessage> {
    override fun encode(buffer: ByteBuf, message: PlayerListItemMessage): ByteBuf {
        buffer.minecraft.writeEnumValue(message.action)
        ByteBufUtils.writeVarInt(buffer, message.players.size)
        for (playerData in message.players) {
            when (message.action) {
                Action.ADD_PLAYER -> {
                    buffer.minecraft.writeUuid(playerData.gameProfile.id)
                    buffer.minecraft.writeString(playerData.gameProfile.name)
                    ByteBufUtils.writeVarInt(buffer, playerData.gameProfile.properties.size())

                    for (property in playerData.gameProfile.properties.values()) {
                        buffer.minecraft.writeString(property.name)
                        buffer.minecraft.writeString(property.value)

                        if (property.hasSignature()) {
                            buffer.writeBoolean(true)
                            buffer.minecraft.writeString(property.signature)
                        } else {
                            buffer.writeBoolean(false)
                        }
                    }

                    buffer.minecraft.writeEnumValue(playerData.gameMode)
                    ByteBufUtils.writeVarInt(buffer, playerData.ping)

                    buffer.writeBoolean(true)
                    buffer.minecraft.writeString(playerData.displayName.asJson())
                }
                Action.REMOVE_PLAYER -> {
                    buffer.minecraft.writeUuid(playerData.gameProfile.id)
                }
                Action.UPDATE_DISPLAY_NAME -> {
                    buffer.minecraft.writeUuid(playerData.gameProfile.id)
                    buffer.writeBoolean(true)
                    buffer.minecraft.writeString(playerData.displayName.asJson())
                }
                Action.UPDATE_GAME_MODE -> {
                    buffer.minecraft.writeUuid(playerData.gameProfile.id)
                    buffer.minecraft.writeEnumValue(playerData.gameMode)
                }
                Action.UPDATE_LATENCY -> {
                    buffer.minecraft.writeUuid(playerData.gameProfile.id)
                    ByteBufUtils.writeVarInt(buffer, playerData.ping)
                }
            }
        }
        return buffer
    }

    override fun decode(buffer: ByteBuf?): PlayerListItemMessage {
        throw IOException("No have decode support for this")
    }
}

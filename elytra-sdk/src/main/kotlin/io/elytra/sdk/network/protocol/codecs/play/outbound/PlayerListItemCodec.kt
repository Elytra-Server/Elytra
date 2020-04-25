package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.Action
import io.elytra.sdk.network.protocol.message.play.outbound.PlayerListItemMessage
import io.elytra.sdk.network.utils.writeEnumValue
import io.elytra.sdk.network.utils.writeString
import io.elytra.sdk.network.utils.writeUuid
import io.netty.buffer.ByteBuf

class PlayerListItemCodec : OutboundCodec<PlayerListItemMessage> {
    override fun encode(buffer: ByteBuf, message: PlayerListItemMessage): ByteBuf {
        buffer.writeEnumValue(message.action)
        ByteBufUtils.writeVarInt(buffer, message.players.size)
        for (playerData in message.players) {
            when (message.action) {
                Action.ADD_PLAYER -> {
                    buffer.writeUuid(playerData.gameProfile.id)
                    buffer.writeString(playerData.gameProfile.name)
                    ByteBufUtils.writeVarInt(buffer, playerData.gameProfile.properties.size())

                    for (property in playerData.gameProfile.properties.values()) {
                        buffer.writeString(property.name)
                        buffer.writeString(property.value)

                        if (property.hasSignature()) {
                            buffer.writeBoolean(true)
                            buffer.writeString(property.signature)
                        } else {
                            buffer.writeBoolean(false)
                        }
                    }

                    buffer.writeEnumValue(playerData.gameMode)
                    ByteBufUtils.writeVarInt(buffer, playerData.ping)

                    buffer.writeBoolean(true)
                    buffer.writeString(playerData.displayName.toJson())
                }
                Action.REMOVE_PLAYER -> {
                    buffer.writeUuid(playerData.gameProfile.id)
                }
                Action.UPDATE_DISPLAY_NAME -> {
                    buffer.writeUuid(playerData.gameProfile.id)
                    buffer.writeBoolean(true)
                    buffer.writeString(playerData.displayName.toJson())
                }
                Action.UPDATE_GAME_MODE -> {
                    buffer.writeUuid(playerData.gameProfile.id)
                    buffer.writeEnumValue(playerData.gameMode)
                }
                Action.UPDATE_LATENCY -> {
                    buffer.writeUuid(playerData.gameProfile.id)
                    ByteBufUtils.writeVarInt(buffer, playerData.ping)
                }
            }
        }

        return buffer
    }
}

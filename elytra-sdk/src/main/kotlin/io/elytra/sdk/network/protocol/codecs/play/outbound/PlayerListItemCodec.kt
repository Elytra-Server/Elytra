package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.api.utils.asJson
import io.elytra.sdk.network.protocol.message.play.Action
import io.elytra.sdk.network.protocol.message.play.PlayerListItemMessage
import io.elytra.sdk.network.protocol.message.play.PlayerPosLookMessage
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.ByteBuf
import java.io.IOException

class PlayerListItemCodec : Codec<PlayerListItemMessage> {
	override fun encode(buffer: ByteBuf, message: PlayerListItemMessage): ByteBuf {
		buffer.minecraft.writeEnumValue(message.action)
		ByteBufUtils.writeVarInt(buffer,message.players.size)
		for (playerData in message.players) {
			when(message.action){
				Action.ADD_PLAYER ->{
					buffer.minecraft.writeUuid(playerData.gameProfile.id)
					buffer.minecraft.writeString(playerData.gameProfile.name)
					ByteBufUtils.writeVarInt(buffer,playerData.gameProfile.properties.size())

					for (property in playerData.gameProfile.properties.values()) {
						buffer.minecraft.writeString(property.name)
						buffer.minecraft.writeString(property.value)

						if(property.hasSignature()){
							buffer.writeBoolean(true)
							buffer.minecraft.writeString(property.signature)
						}else{
							buffer.writeBoolean(false)
						}
					}

					buffer.minecraft.writeEnumValue(playerData.gameMode)
					ByteBufUtils.writeVarInt(buffer,playerData.ping)

					if(playerData.displayName == null){

					}else{
						buffer.writeBoolean(true)
						buffer.minecraft.writeString(playerData.displayName.asJson())
					}
				}
				Action.REMOVE_PLAYER -> {

				}
				Action.UPDATE_DISPLAY_NAME -> {

				}
				Action.UPDATE_GAME_MODE -> {

				}
				Action.UPDATE_LATENCY -> {

				}
			}
		}
		return buffer
	}

	override fun decode(buffer: ByteBuf?): PlayerListItemMessage {
		throw IOException("No have decode support for this")
	}

}

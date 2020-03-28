package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.message.play.ClientSettingsMessage
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.ByteBuf

class ClientSettingsCodec : Codec<ClientSettingsMessage> {

	override fun encode(buffer: ByteBuf, message: ClientSettingsMessage): ByteBuf {
		buffer.minecraft.writeString(message.lang)
		buffer.writeByte(message.view.toInt())
		buffer.writeInt(message.chatVisibility)
		buffer.writeBoolean(message.enableColors)
		buffer.writeByte(message.modelPartFlags.toInt())
		buffer.writeByte(message.mainHand)
		return buffer
	}

	override fun decode(buffer: ByteBuf): ClientSettingsMessage {
		val lang = buffer.minecraft.readString(16)
		val view = buffer.readByte()
		val chatVisibility = ByteBufUtils.readVarInt(buffer)
		val enableColors = buffer.readBoolean()
		val modelPartFlags = buffer.readUnsignedByte()
		val mainHand = ByteBufUtils.readVarInt(buffer)
		return ClientSettingsMessage(lang, view, chatVisibility, enableColors, modelPartFlags, mainHand)
	}

}

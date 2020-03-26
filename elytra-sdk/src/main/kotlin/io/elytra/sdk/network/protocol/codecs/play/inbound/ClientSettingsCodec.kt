package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.ClientSettingsMessage
import io.elytra.sdk.network.utils.ByteUtils
import io.netty.buffer.ByteBuf

class ClientSettingsCodec : Codec<ClientSettingsMessage> {
	override fun encode(buf: ByteBuf, message: ClientSettingsMessage): ByteBuf {
		ByteUtils.writeString(buf, message.lang)
		buf.writeByte(message.view.toInt())
		buf.writeInt(message.chatVisibility)
		buf.writeBoolean(message.enableColors)
		buf.writeByte(message.modelPartFlags.toInt())
		buf.writeByte(message.mainHand)
		return buf
	}

	override fun decode(buffer: ByteBuf): ClientSettingsMessage {
		val lang = ByteUtils.readStringFromBuffer(buffer,16)
		val view = buffer.readByte()
		val chatVisibility = ByteUtils.readVarIntFromBuffer(buffer)
		val enableColors = buffer.readBoolean()
		val modelPartFlags = buffer.readUnsignedByte()
		val mainHand = ByteUtils.readVarIntFromBuffer(buffer)
		return ClientSettingsMessage(lang, view, chatVisibility, enableColors, modelPartFlags, mainHand)
	}

}

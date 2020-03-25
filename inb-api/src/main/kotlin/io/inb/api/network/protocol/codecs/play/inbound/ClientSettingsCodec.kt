package io.inb.api.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.inb.api.network.protocol.message.play.ClientSettingsMessage
import io.inb.api.utils.Utils
import io.netty.buffer.ByteBuf

class ClientSettingsCodec : Codec<ClientSettingsMessage> {
	override fun encode(buf: ByteBuf, message: ClientSettingsMessage): ByteBuf {
		Utils.writeString(buf, message.lang)
		buf.writeByte(message.view.toInt())
		buf.writeInt(message.chatVisibility)
		buf.writeBoolean(message.enableColors)
		buf.writeByte(message.modelPartFlags.toInt())
		buf.writeByte(message.mainHand)
		return buf
	}

	override fun decode(buffer: ByteBuf): ClientSettingsMessage {
		val lang = Utils.readStringFromBuffer(buffer,16)
		val view = buffer.readByte()
		val chatVisibility = Utils.readVarIntFromBuffer(buffer)
		val enableColors = buffer.readBoolean()
		val modelPartFlags = buffer.readUnsignedByte()
		val mainHand = Utils.readVarIntFromBuffer(buffer)
		return ClientSettingsMessage(lang,view,chatVisibility,enableColors,modelPartFlags,mainHand)
	}

}

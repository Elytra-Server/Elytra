package io.inb.api.network.protocol.codecs.play

import com.flowpowered.network.Codec
import io.inb.api.network.protocol.message.play.ClientSettingsMessage
import io.inb.api.utils.Utils
import io.netty.buffer.ByteBuf

class ClientSettingsCodec : Codec<ClientSettingsMessage> {
	override fun encode(buf: ByteBuf, message: ClientSettingsMessage): ByteBuf {
		Utils.writeString(buf, message.lang)
		buf.writeByte(message.view)
		buf.writeInt(message.chatVisibility)
		buf.writeBoolean(message.enableColors)
		buf.writeByte(message.modelPartFlags)
		buf.writeByte(message.mainHand)
		return buf
	}

	override fun decode(buffer: ByteBuf): ClientSettingsMessage {
		val lang = Utils.readStringFromBuffer(buffer,16)
		val view = buffer.readInt()
		val chatVisibility = buffer.readInt()
		val enableColors = buffer.readBoolean()
		val modelPartFlags = buffer.readInt()
		val mainHand = buffer.readInt()
		return ClientSettingsMessage(lang,view,chatVisibility,enableColors,modelPartFlags,mainHand)
	}

}

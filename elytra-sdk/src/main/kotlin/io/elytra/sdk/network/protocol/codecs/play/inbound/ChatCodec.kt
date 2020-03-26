package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.ChatMessage
import io.elytra.sdk.network.utils.ByteUtils
import io.netty.buffer.ByteBuf

class ChatCodec : Codec<ChatMessage> {
	override fun encode(buffer: ByteBuf, message: ChatMessage): ByteBuf {
		ByteUtils.writeString(buffer,message.message)
		return buffer
	}

	override fun decode(buffer: ByteBuf): ChatMessage {
		val json = ByteUtils.readStringFromBuffer(buffer,256)
		return ChatMessage(json)
	}

}

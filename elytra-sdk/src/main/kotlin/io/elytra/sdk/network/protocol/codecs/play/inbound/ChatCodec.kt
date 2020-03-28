package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.ChatMessage
import io.elytra.sdk.network.utils.MinecraftByteBuf
import io.netty.buffer.ByteBuf

class ChatCodec : Codec<ChatMessage> {

	private val ByteBuf.minecraft get() = MinecraftByteBuf(this)

	override fun encode(buffer: ByteBuf, message: ChatMessage): ByteBuf {
		buffer.minecraft.writeString(message.message)
		return buffer
	}

	override fun decode(buffer: ByteBuf): ChatMessage {
		val json = buffer.minecraft.readStringFromBuffer(256)
		return ChatMessage(json)
	}

}

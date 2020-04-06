package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.inbound.ChatMessage
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.ByteBuf

class ChatCodec : Codec<ChatMessage> {

    override fun encode(buffer: ByteBuf, message: ChatMessage): ByteBuf {
        buffer.minecraft.writeString(message.content)
        return buffer
    }

    override fun decode(buffer: ByteBuf): ChatMessage {
        val json = buffer.minecraft.readString(256)
        return ChatMessage(json)
    }
}

package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.inbound.ChatMessage
import io.elytra.sdk.network.utils.readString
import io.elytra.sdk.network.utils.writeString
import io.netty.buffer.ByteBuf

class ChatCodec : Codec<ChatMessage> {

    override fun encode(buffer: ByteBuf, message: ChatMessage): ByteBuf {
        buffer.writeString(message.content)
        return buffer
    }

    override fun decode(buffer: ByteBuf): ChatMessage {
        val json = buffer.readString(256)
        return ChatMessage(json)
    }
}

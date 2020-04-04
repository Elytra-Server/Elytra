package io.elytra.sdk.network.protocol.codecs.status

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.status.ServerQueryMessage
import io.netty.buffer.ByteBuf

class ServerQueryCodec : Codec<ServerQueryMessage> {
    override fun encode(buf: ByteBuf, message: ServerQueryMessage): ByteBuf {
        return buf
    }

    override fun decode(buffer: ByteBuf): ServerQueryMessage {
        return ServerQueryMessage()
    }
}

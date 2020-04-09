package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.outbound.UnloadChunkMessage
import io.netty.buffer.ByteBuf

class UnloadChunkCodec : Codec<UnloadChunkMessage> {

    override fun encode(buf: ByteBuf, message: UnloadChunkMessage): ByteBuf {
        buf.writeInt(message.chunkX)
        buf.writeInt(message.chunkY)

        return buf
    }

    override fun decode(buffer: ByteBuf): UnloadChunkMessage {
        return UnloadChunkMessage(buffer.readInt(), buffer.readInt())
    }
}

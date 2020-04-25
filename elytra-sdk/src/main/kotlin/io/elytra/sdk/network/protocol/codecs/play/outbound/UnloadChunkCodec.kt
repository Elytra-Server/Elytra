package io.elytra.sdk.network.protocol.codecs.play.outbound

import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.UnloadChunkMessage
import io.netty.buffer.ByteBuf

class UnloadChunkCodec : OutboundCodec<UnloadChunkMessage> {
    override fun encode(buf: ByteBuf, message: UnloadChunkMessage): ByteBuf {
        buf.writeInt(message.chunkX)
        buf.writeInt(message.chunkY)

        return buf
    }
}

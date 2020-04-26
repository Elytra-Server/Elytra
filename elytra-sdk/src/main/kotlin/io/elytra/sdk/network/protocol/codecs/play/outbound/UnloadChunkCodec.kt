package io.elytra.sdk.network.protocol.codecs.play.outbound

import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.UnloadChunkMessage
import io.netty.buffer.ByteBuf

class UnloadChunkCodec : OutboundCodec<UnloadChunkMessage>() {
    override fun encode(buffer: ByteBuf, message: UnloadChunkMessage): ByteBuf {
        buffer.writeInt(message.chunkX)
        buffer.writeInt(message.chunkY)

        return buffer
    }
}

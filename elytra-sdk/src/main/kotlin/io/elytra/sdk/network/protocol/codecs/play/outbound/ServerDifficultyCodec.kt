package io.elytra.sdk.network.protocol.codecs.play.outbound

import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.ServerDifficultyMessage
import io.netty.buffer.ByteBuf

class ServerDifficultyCodec : OutboundCodec<ServerDifficultyMessage>() {
    override fun encode(buffer: ByteBuf, message: ServerDifficultyMessage): ByteBuf {
        buffer.writeByte(message.difficulty.ordinal)
        return buffer
    }
}

package io.elytra.sdk.network.protocol.codecs.play.outbound

import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.SpawnPositionMessage
import io.netty.buffer.ByteBuf

class SpawnPositionCodec : OutboundCodec<SpawnPositionMessage>() {
    override fun encode(buffer: ByteBuf, message: SpawnPositionMessage): ByteBuf {
        buffer.writeLong((message.x and 0x3ffffff shl 38 or (message.y and 0xfff shl 26) or (message.z and 0x3ffffff)).toLong())

        return buffer
    }
}

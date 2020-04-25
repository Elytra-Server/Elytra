package io.elytra.sdk.network.protocol.codecs.play.outbound

import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.SpawnPositionMessage
import io.netty.buffer.ByteBuf

class SpawnPositionCodec : OutboundCodec<SpawnPositionMessage> {
    override fun encode(buf: ByteBuf, message: SpawnPositionMessage): ByteBuf {
        buf.writeLong((message.x and 0x3ffffff shl 38 or (message.y and 0xfff shl 26) or (message.z and 0x3ffffff)).toLong())

        return buf
    }
}

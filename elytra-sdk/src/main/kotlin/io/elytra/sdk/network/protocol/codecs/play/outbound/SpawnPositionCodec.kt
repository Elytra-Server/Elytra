package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.outbound.SpawnPositionMessage
import io.netty.buffer.ByteBuf

class SpawnPositionCodec : Codec<SpawnPositionMessage> {
    override fun encode(buf: ByteBuf, message: SpawnPositionMessage): ByteBuf {
        buf.writeLong((message.x and 0x3ffffff shl 38 or (message.y and 0xfff shl 26) or (message.z and 0x3ffffff)).toLong())

        return buf
    }

    override fun decode(buffer: ByteBuf): SpawnPositionMessage {
        val value: Long = buffer.readLong()
        val x = value shr 38
        val y = value shr 26 and 0xfff
        val z = value shl 38 shr 38

        return SpawnPositionMessage(x.toInt(), y.toInt(), z.toInt())
    }
}

package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.outbound.ChunkDataMessage
import io.netty.buffer.ByteBuf
import java.io.IOException

class ChunkDataCodec : Codec<ChunkDataMessage> {

    override fun encode(buf: ByteBuf, message: ChunkDataMessage): ByteBuf {
        buf.writeInt(message.x)
        buf.writeInt(message.z)
        message.chunk.write(buf)

        return buf
    }

    override fun decode(buffer: ByteBuf): ChunkDataMessage {
        throw IOException("No decode support for this")
    }
}

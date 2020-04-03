package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.message.play.ChunkDataMessage
import io.netty.buffer.ByteBuf
import java.io.IOException

class ChunkDataCodec : Codec<ChunkDataMessage> {

    override fun encode(buf: ByteBuf, message: ChunkDataMessage): ByteBuf {
        buf.writeInt(message.x)
        buf.writeInt(message.z)
        val data = message.data

        try {
            ByteBufUtils.writeVarInt(buf, data.writerIndex())
            buf.writeBytes(data)
        } finally {
            data.release()
        }

        return buf
    }

    override fun decode(buffer: ByteBuf): ChunkDataMessage {
        throw IOException("No decode support for this")
    }
}

package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.message.play.outbound.SetCompressionMessage
import io.netty.buffer.ByteBuf

class SetCompressionCodec : Codec<SetCompressionMessage> {
    override fun encode(buf: ByteBuf, message: SetCompressionMessage): ByteBuf {
        ByteBufUtils.writeVarInt(buf, message.threshold)

        return buf
    }

    override fun decode(buffer: ByteBuf): SetCompressionMessage {
        val threshold = ByteBufUtils.readVarInt(buffer)
        return SetCompressionMessage(threshold)
    }
}

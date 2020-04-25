package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.SetCompressionMessage
import io.netty.buffer.ByteBuf

class SetCompressionCodec : OutboundCodec<SetCompressionMessage> {
    override fun encode(buf: ByteBuf, message: SetCompressionMessage): ByteBuf {
        ByteBufUtils.writeVarInt(buf, message.threshold)

        return buf
    }
}

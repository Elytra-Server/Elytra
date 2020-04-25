package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.OutboundChatMessage
import io.netty.buffer.ByteBuf

class OutboundChatCodec : OutboundCodec<OutboundChatMessage> {
    override fun encode(buf: ByteBuf, message: OutboundChatMessage): ByteBuf {
        ByteBufUtils.writeUTF8(buf, message.content)
        buf.writeByte(message.mode)

        return buf
    }
}

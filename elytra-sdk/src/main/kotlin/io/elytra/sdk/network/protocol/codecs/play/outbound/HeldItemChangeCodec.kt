package io.elytra.sdk.network.protocol.codecs.play.outbound

import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.HeldItemChangeMessage
import io.netty.buffer.ByteBuf

class HeldItemChangeCodec : OutboundCodec<HeldItemChangeMessage> {
    override fun encode(buffer: ByteBuf, message: HeldItemChangeMessage): ByteBuf {
        buffer.writeByte(message.heldItemHotbarIndex)

        return buffer
    }
}

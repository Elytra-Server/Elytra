package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.outbound.HeldItemChangeMessage
import io.netty.buffer.ByteBuf
import java.io.IOException

class HeldItemChangeCodec : Codec<HeldItemChangeMessage> {
    override fun encode(buffer: ByteBuf, message: HeldItemChangeMessage): ByteBuf {
        buffer.writeByte(message.heldItemHotbarIndex)
        return buffer
    }

    override fun decode(buffer: ByteBuf): HeldItemChangeMessage {
        throw IOException("No have decode support for this")
    }
}

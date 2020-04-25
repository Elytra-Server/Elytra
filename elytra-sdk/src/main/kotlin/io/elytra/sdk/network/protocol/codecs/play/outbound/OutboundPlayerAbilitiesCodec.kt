package io.elytra.sdk.network.protocol.codecs.play.outbound

import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.OutboundPlayerAbilitiesMessage
import io.netty.buffer.ByteBuf
import kotlin.experimental.or

class OutboundPlayerAbilitiesCodec : OutboundCodec<OutboundPlayerAbilitiesMessage> {
    override fun encode(buffer: ByteBuf, message: OutboundPlayerAbilitiesMessage): ByteBuf {
        var b0: Byte = 0

        if (message.invulnerable) {
            b0 = (b0 or 1)
        }

        if (message.flying) {
            b0 = (b0 or 2)
        }

        if (message.allowFlying) {
            b0 = (b0 or 4)
        }

        if (message.creativeMode) {
            b0 = (b0 or 8)
        }

        buffer.writeByte(b0.toInt())
        buffer.writeFloat(message.flySpeed)
        buffer.writeFloat(message.walkSpeed)

        return buffer
    }
}

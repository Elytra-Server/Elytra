package io.elytra.sdk.network.protocol.codecs.play.outbound

import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.TitleMessage
import io.elytra.sdk.network.protocol.message.play.outbound.TitleMessage.Type
import io.elytra.sdk.network.utils.writeEnumValue
import io.elytra.sdk.network.utils.writeTextComponent
import io.netty.buffer.ByteBuf

class TitleCodec : OutboundCodec<TitleMessage>() {
    override fun encode(buffer: ByteBuf, message: TitleMessage): ByteBuf {
        val type = message.type
        buffer.writeEnumValue(type)

        if (type == Type.TITLE || type == Type.SUBTITLE || type == Type.ACTIONBAR)
            buffer.writeTextComponent(message.message!!)

        if (type == Type.TIMES) {
            buffer.writeInt(message.fadeInTime)
            buffer.writeInt(message.displayTime)
            buffer.writeInt(message.fadeOutTime)
        }

        return buffer
    }
}

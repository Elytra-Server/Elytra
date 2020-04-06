package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import io.elytra.api.chat.TextComponent
import io.elytra.sdk.network.protocol.message.play.outbound.TitleMessage
import io.elytra.sdk.network.protocol.message.play.outbound.TitleMessage.Type
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.ByteBuf

class TitleCodec : Codec<TitleMessage> {

    override fun encode(buffer: ByteBuf, message: TitleMessage): ByteBuf {
        val type = message.type
        buffer.minecraft.writeEnumValue(type)

        if (type == Type.TITLE || type == Type.SUBTITLE || type == Type.ACTIONBAR)
            buffer.minecraft.writeTextComponent(message.message!!)

        if (type == Type.TIMES) {
            buffer.writeInt(message.fadeInTime)
            buffer.writeInt(message.displayTime)
            buffer.writeInt(message.fadeOutTime)
        }

        return buffer
    }

    override fun decode(buffer: ByteBuf): TitleMessage {
        val type = buffer.minecraft.readEnumValue(Type::class.java)
        var message: TextComponent? = null
        var fadeInTime = 0
        var displayTime = 0
        var fadeOutTime = 0

        if (type == Type.TITLE || type == Type.SUBTITLE || type == Type.ACTIONBAR)
            message = buffer.minecraft.readTextComponent()

        if (type == Type.TIMES) {
            fadeInTime = buffer.readInt()
            displayTime = buffer.readInt()
            fadeOutTime = buffer.readInt()
        }

        return TitleMessage(type, message, fadeInTime, displayTime, fadeOutTime)
    }
}

package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.message.play.outbound.TabCompleteResponseMessage
import io.netty.buffer.ByteBuf
import io.netty.handler.codec.DecoderException

class TabCompleteResponseCodec : Codec<TabCompleteResponseMessage> {

    override fun encode(buf: ByteBuf, message: TabCompleteResponseMessage): ByteBuf {
        ByteBufUtils.writeVarInt(buf, message.transactionId)
        ByteBufUtils.writeVarInt(buf, message.startIndex)
        ByteBufUtils.writeVarInt(buf, message.textLength)
        ByteBufUtils.writeVarInt(buf, message.completions.size)

        for (completion in message.completions) {
            ByteBufUtils.writeUTF8(buf, completion.match)
            val hasToolip = completion.tooltip != null

            buf.writeBoolean(hasToolip)

            if (hasToolip) {
                ByteBufUtils.writeUTF8(buf, completion.tooltip.text)
            }
        }

        return buf
    }

    override fun decode(buffer: ByteBuf): TabCompleteResponseMessage {
        throw DecoderException("No decode available")
    }
}

package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.TabCompleteResponseMessage
import io.netty.buffer.ByteBuf

class TabCompleteResponseCodec : OutboundCodec<TabCompleteResponseMessage>() {

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
                ByteBufUtils.writeUTF8(buf, completion.tooltip.toJson())
            }
        }

        return buf
    }
}

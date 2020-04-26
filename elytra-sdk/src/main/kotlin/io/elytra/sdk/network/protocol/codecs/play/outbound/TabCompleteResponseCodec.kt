package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.TabCompleteResponseMessage
import io.netty.buffer.ByteBuf

class TabCompleteResponseCodec : OutboundCodec<TabCompleteResponseMessage> {
    override fun encode(buf: ByteBuf, message: TabCompleteResponseMessage): ByteBuf {
        ByteBufUtils.writeVarInt(buf, message.transactionId)
        ByteBufUtils.writeVarInt(buf, message.tabCompletion.startIndex)
        ByteBufUtils.writeVarInt(buf, message.tabCompletion.textLength)
        ByteBufUtils.writeVarInt(buf, message.tabCompletion.completions.size)

        for (completion in message.tabCompletion.completions) {
            ByteBufUtils.writeUTF8(buf, completion.match)
            completion.tooltip.also { tooltip ->
                if (tooltip != null) {
                    // Completion has tooltip
                    buf.writeBoolean(true)
                    ByteBufUtils.writeUTF8(buf, tooltip.toJson())
                } else {
                    // Completion doesn't have tooltip
                    buf.writeBoolean(false)
                }
            }
        }

        return buf
    }
}

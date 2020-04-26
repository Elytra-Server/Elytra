package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.TabCompleteResponseMessage
import io.netty.buffer.ByteBuf

class TabCompleteResponseCodec : OutboundCodec<TabCompleteResponseMessage>() {
    override fun encode(buffer: ByteBuf, message: TabCompleteResponseMessage): ByteBuf {
        ByteBufUtils.writeVarInt(buffer, message.transactionId)
        ByteBufUtils.writeVarInt(buffer, message.tabCompletion.startIndex)
        ByteBufUtils.writeVarInt(buffer, message.tabCompletion.textLength)
        ByteBufUtils.writeVarInt(buffer, message.tabCompletion.completions.size)

        for (completion in message.tabCompletion.completions) {
            ByteBufUtils.writeUTF8(buffer, completion.match)
            completion.tooltip.also { tooltip ->
                if (tooltip != null) {
                    // Completion has tooltip
                    buffer.writeBoolean(true)
                    ByteBufUtils.writeUTF8(buffer, tooltip.toJson())
                } else {
                    // Completion doesn't have tooltip
                    buffer.writeBoolean(false)
                }
            }
        }

        return buffer
    }
}

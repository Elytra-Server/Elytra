package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.message.play.outbound.TabCompleteMessage
import io.netty.buffer.ByteBuf

class TabCompleteCodec : Codec<TabCompleteMessage> {

    override fun encode(buf: ByteBuf, message: TabCompleteMessage): ByteBuf {
        ByteBufUtils.writeVarInt(buf, message.transactionId)
        ByteBufUtils.writeUTF8(buf, message.text)

        return buf
    }

    override fun decode(buffer: ByteBuf): TabCompleteMessage {
        val transactionId = ByteBufUtils.readVarInt(buffer)
        val text = ByteBufUtils.readUTF8(buffer)

        return TabCompleteMessage(transactionId, text)
    }
}

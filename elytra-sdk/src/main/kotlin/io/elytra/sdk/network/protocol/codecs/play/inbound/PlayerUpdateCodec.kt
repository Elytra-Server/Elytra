package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.inbound.PlayerUpdateMessage
import io.netty.buffer.ByteBuf

class PlayerUpdateCodec : Codec<PlayerUpdateMessage> {

    override fun encode(buf: ByteBuf, message: PlayerUpdateMessage): ByteBuf {
        buf.writeBoolean(message.onGround)
        return buf
    }

    override fun decode(buffer: ByteBuf): PlayerUpdateMessage {
        val onGround = buffer.readBoolean()
        return PlayerUpdateMessage(onGround)
    }
}

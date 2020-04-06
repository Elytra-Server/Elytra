package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.inbound.PlayerMovementMessage
import io.netty.buffer.ByteBuf

class PlayerUpdateCodec : Codec<PlayerMovementMessage> {

    override fun encode(buf: ByteBuf, message: PlayerMovementMessage): ByteBuf {
        buf.writeBoolean(message.onGround)
        return buf
    }

    override fun decode(buffer: ByteBuf): PlayerMovementMessage {
        val onGround = buffer.readBoolean()
        return PlayerMovementMessage(onGround)
    }
}

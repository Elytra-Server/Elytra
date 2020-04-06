package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.inbound.PlayerPositionMessage
import io.netty.buffer.ByteBuf

class PlayerPositionCodec : Codec<PlayerPositionMessage> {

    override fun encode(buf: ByteBuf, message: PlayerPositionMessage): ByteBuf {
        buf.writeDouble(message.x)
        buf.writeDouble(message.y)
        buf.writeDouble(message.z)
        buf.writeBoolean(message.onGround)

        return buf
    }

    override fun decode(buffer: ByteBuf): PlayerPositionMessage {
        val x = buffer.readDouble()
        val y = buffer.readDouble()
        val z = buffer.readDouble()
        val onGround = buffer.readBoolean()

        return PlayerPositionMessage(x, y, z, onGround)
    }
}

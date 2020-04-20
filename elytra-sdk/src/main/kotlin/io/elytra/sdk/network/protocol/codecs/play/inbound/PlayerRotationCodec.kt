package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.inbound.PlayerRotationMessage
import io.netty.buffer.ByteBuf

class PlayerRotationCodec : Codec<PlayerRotationMessage> {
    override fun encode(buf: ByteBuf, message: PlayerRotationMessage): ByteBuf {
        buf.writeFloat(message.yaw)
        buf.writeFloat(message.pitch)
        buf.writeBoolean(message.onGround)

        return buf
    }

    override fun decode(buffer: ByteBuf): PlayerRotationMessage {
        var yaw = buffer.readFloat()
        yaw = (yaw % 360 + 360) % 360

        val pitch = buffer.readFloat()
        val onGround = buffer.readBoolean()
        return PlayerRotationMessage(yaw, pitch, onGround)
    }
}

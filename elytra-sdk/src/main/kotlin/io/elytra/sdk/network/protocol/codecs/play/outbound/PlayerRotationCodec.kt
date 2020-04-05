package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.message.play.PlayerRotationMessage
import io.netty.buffer.ByteBuf
import java.io.IOException

class PlayerRotationCodec : Codec<PlayerRotationMessage> {

    override fun encode(buffer: ByteBuf, message: PlayerRotationMessage): ByteBuf {
        buffer.writeDouble(message.x)
        buffer.writeDouble(message.y)
        buffer.writeDouble(message.z)
        buffer.writeFloat(message.yaw)
        buffer.writeFloat(message.pitch)
        buffer.writeByte(message.flags.toInt())
        ByteBufUtils.writeVarInt(buffer, message.teleportId)

        return buffer
    }

    override fun decode(buffer: ByteBuf): PlayerRotationMessage {
        throw IOException("No have decode support for this")
    }
}

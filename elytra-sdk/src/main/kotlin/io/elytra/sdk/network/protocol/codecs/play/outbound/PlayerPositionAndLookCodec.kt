package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.message.play.outbound.PlayerPositionAndLookMessage
import io.netty.buffer.ByteBuf

class PlayerPositionAndLookCodec : Codec<PlayerPositionAndLookMessage> {

    override fun encode(buffer: ByteBuf, message: PlayerPositionAndLookMessage): ByteBuf {
        buffer.writeDouble(message.x)
        buffer.writeDouble(message.y)
        buffer.writeDouble(message.z)
        buffer.writeFloat(message.yaw)
        buffer.writeFloat(message.pitch)
        buffer.writeByte(message.flags.toInt())
        ByteBufUtils.writeVarInt(buffer, message.teleportId)

        return buffer
    }

    override fun decode(buffer: ByteBuf): PlayerPositionAndLookMessage {
        val x = buffer.readDouble()
        val y = buffer.readDouble()
        val z = buffer.readDouble()
        val yaw = buffer.readFloat()
        val pitch = buffer.readFloat()
        val flags = buffer.readByte()
        val teleportId = ByteBufUtils.readVarInt(buffer)

        return PlayerPositionAndLookMessage(x, y, z, yaw, pitch, flags, teleportId)
    }
}

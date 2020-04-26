package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.PlayerPositionAndLookMessage
import io.netty.buffer.ByteBuf

class PlayerPositionAndLookCodec : OutboundCodec<PlayerPositionAndLookMessage>() {
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
}

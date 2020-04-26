package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.SpawnPlayerMessage
import io.elytra.sdk.network.utils.writeUuid
import io.netty.buffer.ByteBuf
import kotlin.math.roundToInt

class SpawnPlayerCodec : OutboundCodec<SpawnPlayerMessage>() {
    override fun encode(buffer: ByteBuf, message: SpawnPlayerMessage): ByteBuf {
        ByteBufUtils.writeVarInt(buffer, message.id)
        buffer.writeUuid(message.uuid)
        buffer.writeDouble(message.x)
        buffer.writeDouble(message.y)
        buffer.writeDouble(message.z)
        buffer.writeByte(message.yaw.roundToInt())
        buffer.writeByte(message.pitch.roundToInt())

        return buffer
    }
}

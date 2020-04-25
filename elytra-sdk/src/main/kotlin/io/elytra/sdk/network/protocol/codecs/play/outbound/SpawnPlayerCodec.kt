package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.SpawnPlayerMessage
import io.elytra.sdk.network.utils.writeUuid
import io.netty.buffer.ByteBuf
import kotlin.math.roundToInt

class SpawnPlayerCodec : OutboundCodec<SpawnPlayerMessage> {
    override fun encode(buf: ByteBuf, message: SpawnPlayerMessage): ByteBuf {
        ByteBufUtils.writeVarInt(buf, message.id)
        buf.writeUuid(message.uuid)
        buf.writeDouble(message.x)
        buf.writeDouble(message.y)
        buf.writeDouble(message.z)
        buf.writeByte(message.yaw.roundToInt())
        buf.writeByte(message.pitch.roundToInt())

        return buf
    }
}

package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.message.play.SpawnPlayerMessage
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.ByteBuf
import java.util.*
import kotlin.math.roundToInt

class SpawnPlayerCodec : Codec<SpawnPlayerMessage> {

    override fun encode(buf: ByteBuf, message: SpawnPlayerMessage): ByteBuf {
        ByteBufUtils.writeVarInt(buf, message.id)
        buf.minecraft.writeUuid(message.uuid)
        buf.writeDouble(message.x)
        buf.writeDouble(message.y)
        buf.writeDouble(message.z)
        buf.writeByte(message.yaw.roundToInt())
        buf.writeByte(message.pitch.roundToInt())

        return buf
    }

    override fun decode(buffer: ByteBuf): SpawnPlayerMessage {
        val id: Int = buffer.readInt()
        val uuid: UUID = buffer.minecraft.readUuid()
        val x: Double = buffer.readDouble()
        val y: Double = buffer.readDouble()
        val z: Double = buffer.readDouble()
        val yaw: Float = buffer.readByte().toFloat()
        val pitch: Float = buffer.readByte().toFloat()

        return SpawnPlayerMessage(id, uuid, x, y, z, yaw, pitch)
    }
}

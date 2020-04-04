package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.Codec
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.message.play.JoinGameMessage
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.ByteBuf
import java.io.IOException

class JoinGameCodec : Codec<JoinGameMessage> {

    override fun encode(buffer: ByteBuf, message: JoinGameMessage): ByteBuf {
        buffer.writeInt(message.id)
        buffer.minecraft.writeEnumValue(message.gameMode)
        buffer.writeInt(message.dimension)
        buffer.writeLong(message.seed)
        buffer.writeByte(message.maxPlayers)
        buffer.minecraft.writeString(message.worldType)
        ByteBufUtils.writeVarInt(buffer, message.viewDistance)
        buffer.writeBoolean(message.reducedDebugInfo)
        buffer.writeBoolean(message.respawnScreen)
        return buffer
    }

    override fun decode(buffer: ByteBuf): JoinGameMessage {
        throw IOException("No have decode support for this")
    }
}

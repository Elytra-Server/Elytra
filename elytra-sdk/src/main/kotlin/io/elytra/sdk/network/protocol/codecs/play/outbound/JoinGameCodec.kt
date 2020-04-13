package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.JoinGameMessage
import io.elytra.sdk.network.utils.minecraft
import io.netty.buffer.ByteBuf

class JoinGameCodec : OutboundCodec<JoinGameMessage>() {

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
}

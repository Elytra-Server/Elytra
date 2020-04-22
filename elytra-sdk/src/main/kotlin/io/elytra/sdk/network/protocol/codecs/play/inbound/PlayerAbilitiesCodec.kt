package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.inbound.PlayerAbilitiesMessage
import io.netty.buffer.ByteBuf

class PlayerAbilitiesCodec : Codec<PlayerAbilitiesMessage> {
    override fun encode(buffer: ByteBuf, message: PlayerAbilitiesMessage): ByteBuf {
        return buffer
    }

    override fun decode(buffer: ByteBuf): PlayerAbilitiesMessage {
        val flags: Int = buffer.readUnsignedByte().toInt()
        val flyingSpeed = buffer.readFloat()
        val walkingSpeed = buffer.readFloat()

        return PlayerAbilitiesMessage(
            flags,
            flyingSpeed,
            walkingSpeed
        )
    }
}

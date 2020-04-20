package io.elytra.sdk.network.protocol.codecs.play.inbound

import com.flowpowered.network.Codec
import io.elytra.sdk.network.protocol.message.play.inbound.PlayerAbilitiesMessage
import io.netty.buffer.ByteBuf

class PlayerAbilitiesCodec : Codec<PlayerAbilitiesMessage> {
    override fun encode(buffer: ByteBuf, message: PlayerAbilitiesMessage): ByteBuf {
        return buffer
    }

    override fun decode(buffer: ByteBuf): PlayerAbilitiesMessage {
        // TODO - Handle flags
        // Bit mask. 0x08: damage disabled (god mode), 0x04: can fly, 0x02: is flying, 0x01: is Creative
        val flags = buffer.readByte()
        val flyingSpeed = buffer.readFloat()
        val walkingSpeed = buffer.readFloat()

        return PlayerAbilitiesMessage(
            invulnerable = false,
            flying = false,
            allowFlying = false,
            creativeMode = false,
            flySpeed = flyingSpeed,
            walkSpeed = walkingSpeed
        )
    }
}

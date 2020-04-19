package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message

data class OutboundPlayerAbilitiesMessage(
    val invulnerable: Boolean,
    val flying: Boolean,
    val allowFlying: Boolean,
    val creativeMode: Boolean,
    val flySpeed: Float,
    val walkSpeed: Float
) : Message

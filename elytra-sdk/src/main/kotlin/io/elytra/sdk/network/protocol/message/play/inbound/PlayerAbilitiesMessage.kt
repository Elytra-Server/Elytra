package io.elytra.sdk.network.protocol.message.play.inbound

import com.flowpowered.network.Message

data class PlayerAbilitiesMessage(
    val flags: Int,
    val flySpeed: Float,
    val walkSpeed: Float
) : Message

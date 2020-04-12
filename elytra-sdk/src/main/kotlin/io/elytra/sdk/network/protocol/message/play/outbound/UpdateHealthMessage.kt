package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message

data class UpdateHealthMessage(
    val health: Float,
    val food: Int,
    val foodSaturation: Float
) : Message

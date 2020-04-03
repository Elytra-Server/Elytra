package io.elytra.sdk.network.protocol.message.play

import com.flowpowered.network.Message

data class ConfirmTeleportMessage(
    val telportId: Int
) : Message

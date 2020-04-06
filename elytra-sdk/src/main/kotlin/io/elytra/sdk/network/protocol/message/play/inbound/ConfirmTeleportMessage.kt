package io.elytra.sdk.network.protocol.message.play.inbound

import com.flowpowered.network.Message

data class ConfirmTeleportMessage(
    val telportId: Int
) : Message

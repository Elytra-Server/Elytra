package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message

data class EntityAnimationMessage(
    val entityId: Int = 0,
    val animation: Int = 0
) : Message

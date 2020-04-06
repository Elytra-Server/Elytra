package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message
import io.elytra.api.world.Position

data class EntityTeleportMessage(
    val entityId: Int = 0,
    val position: Position,
    val ground: Boolean
) : Message

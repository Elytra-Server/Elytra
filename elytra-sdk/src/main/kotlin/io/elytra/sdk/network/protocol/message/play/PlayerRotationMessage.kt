package io.elytra.sdk.network.protocol.message.play

import com.flowpowered.network.Message
import io.elytra.api.world.Position

data class PlayerRotationMessage(
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float,
    val pitch: Float,
    val flags: Byte = 0,
    val teleportId: Int = 0
) : Message {

    constructor(position: Position) : this(position.x, position.y, position.z, position.yaw, position.pitch)
}

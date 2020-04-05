package io.elytra.sdk.network.protocol.message.play

import com.flowpowered.network.Message
import io.elytra.api.world.Position

class PlayerPositionMessage(
    val x: Double,
    val y: Double,
    val z: Double,
    val onGround: Boolean
) : Message {

    fun update(position: Position) {
        position.x = x
        position.y = y
        position.z = z
    }
}

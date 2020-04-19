package io.elytra.sdk.network.protocol.message.play.inbound

import io.elytra.api.world.Position

class PlayerRotationMessage(
    val yaw: Float = 0f,
    val pitch: Float,
    onGround: Boolean
) : PlayerMovementMessage(onGround) {

    override fun update(record: Position) {
        record.yaw = yaw
        record.pitch = pitch
    }

    override fun toString(): String {
        return "PlayerRotationMessage(yaw=$yaw,pitch=$pitch,onGround=$onGround)"
    }
}

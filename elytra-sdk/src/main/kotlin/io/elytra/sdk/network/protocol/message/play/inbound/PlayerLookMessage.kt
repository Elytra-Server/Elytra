package io.elytra.sdk.network.protocol.message.play.inbound

import io.elytra.api.world.Position

class PlayerLookMessage(
    x: Double,
    feetY: Double,
    z: Double,
    onGround: Boolean,
    val yaw: Float = 0f,
    val pitch: Float
) : PlayerPositionMessage(x, feetY, z, onGround) {

    override fun update(record: Position) {
        record.x = x
        record.y = feetY
        record.z = z
        record.yaw = yaw
        record.pitch = pitch
    }

    override fun toString(): String {
        return "PlayerLookMessage(x=$x,y=$feetY,z=$z,yaw=$yaw,pitch=$pitch,onGround=$onGround)"
    }
}

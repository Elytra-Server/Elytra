package io.elytra.sdk.network.protocol.message.play.inbound

import io.elytra.api.world.Position

class PlayerLookMessage(
    var yaw: Float = 0f,
    var pitch: Float,
    onGround: Boolean
) : PlayerMovementMessage(onGround) {

    init {
        this.yaw = (yaw % 360 + 360) % 360
    }

    override fun update(record: Position) {
        record.yaw = yaw
        record.pitch = pitch
    }
}

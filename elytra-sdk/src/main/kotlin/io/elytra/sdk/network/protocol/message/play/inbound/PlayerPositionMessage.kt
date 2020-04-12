package io.elytra.sdk.network.protocol.message.play.inbound

import io.elytra.api.world.Position

open class PlayerPositionMessage(
    val x: Double,
    val feetY: Double,
    val z: Double,
    onGround: Boolean
) : PlayerMovementMessage(onGround) {

    override fun update(record: Position) {
        record.x = x
        record.y = feetY
        record.z = z
    }
}

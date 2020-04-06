package io.elytra.sdk.network.protocol.message.play.inbound

import io.elytra.api.world.Position

class PlayerPositionMessage(
    val x: Double,
    val y: Double,
    val z: Double,
    onGround: Boolean
) : PlayerUpdateMessage(onGround) {

    override fun update(record: Position) {
        record.x = x
        record.y = y
        record.z = z
    }
}

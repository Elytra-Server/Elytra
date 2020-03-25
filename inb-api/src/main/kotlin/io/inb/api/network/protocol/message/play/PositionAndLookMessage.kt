package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message
import io.inb.api.world.Location

data class PositionAndLookMessage(
	val x: Double,
	val y: Double,
	val z: Double,
	val yaw: Float,
	val pitch: Float,
	val isGrounded: Boolean
) : Message {

	constructor(location: Location) : this(location.x, location.y, location.z, location.yaw, location.pitch, location.y <= 0)
}

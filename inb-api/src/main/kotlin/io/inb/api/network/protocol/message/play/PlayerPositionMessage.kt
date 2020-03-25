package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message
import io.inb.api.utils.Updatable
import io.inb.api.world.Location

data class PlayerPositionMessage(
	val x: Double,
	val y: Double,
	val z: Double,
	val grounded: Boolean
) : Message, Updatable<Location> {

	override fun update(arg: Location) {
		arg.x = x
		arg.y = y
		arg.z = z
	}
}

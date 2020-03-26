package io.inb.api.world

import java.io.Serializable

data class Location(
	var x: Double,
	var y: Double,
	var z: Double,
	val yaw: Float,
	val pitch: Float,
	val world: String
) : Cloneable, Serializable {
	companion object {
		val EMPTY: Location = Location(0.0, 0.0, 0.0, 0.0f, 0.0f, "world")
	}

	public override fun clone(): Location {
		return super.clone() as Location
	}
}


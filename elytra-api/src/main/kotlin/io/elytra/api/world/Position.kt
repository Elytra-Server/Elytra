package io.elytra.api.world

import java.io.Serializable

data class Position(
    var x: Double,
    var y: Double,
    var z: Double,
    val yaw: Float,
    val pitch: Float
) : Cloneable, Serializable {
    companion object {
        val EMPTY: Position = Position(0.0, 0.0, 0.0, 0.0f, 0.0f)
    }

    constructor(x: Double, y: Double, z: Double) : this(x, y, z, 0f, 0f)

    public override fun clone(): Position {
        return super.clone() as Position
    }
}

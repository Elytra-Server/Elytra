package io.elytra.api.world

import java.io.Serializable

/**
 * Represents a 3D position in world space
 */
data class Position(
    var x: Double,
    var y: Double,
    var z: Double,
    var yaw: Float,
    var pitch: Float
) : Cloneable, Serializable {
    companion object {
        val EMPTY: Position = Position(0.0, 0.0, 0.0, 0.0f, 0.0f)
    }

    constructor(x: Double, y: Double, z: Double) : this(x, y, z, 0f, 0f)

    public override fun clone(): Position {
        return super.clone() as Position
    }
}

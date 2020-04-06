package io.elytra.api.entity

import io.elytra.api.nbt.tags.NbtCompound
import io.elytra.api.world.Position
import io.elytra.api.world.World

interface Entity {
    /*var id: Long
	var health: Double
	var maxHealth: Double
	var damage: Double
	var maxDamage: Double

	var exp: Int
	var expLevel: Int

	var collided: Boolean
	var collidedHorizontally: Boolean
	var collidedVertically: Boolean*/

    var networkId: Int

    var state: EntityState
    var namedTag: NbtCompound

    var world: String
    var position: Position

    fun teleport(position: Position)
    fun teleport(position: Position, world: World)
    fun kill()

    fun move(position: Position)
}

enum class EntityState {
    IDLE,
    IMMOBILE,
    SNEAKING,
    SWIMMING,
    SPRINTING,
    GLIDING,
    CLIMBING,
    DEAD
}

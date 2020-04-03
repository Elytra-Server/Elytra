package io.elytra.sdk.entity

import io.elytra.api.entity.Entity
import io.elytra.api.entity.EntityState
import io.elytra.api.nbt.tags.NbtCompound
import io.elytra.api.world.Position
import io.elytra.api.world.World

abstract class ElytraEntity (
	override var networkId: Int,
	override var state: EntityState = EntityState.IDLE,
	override var namedTag: NbtCompound = NbtCompound(""),
	override var world: String = "world",
	override var position: Position = Position.EMPTY
): Entity{

	override fun teleport(position: io.elytra.api.world.Position) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun teleport(position: Position, world: World) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun kill() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

}

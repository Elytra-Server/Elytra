package io.elytra.sdk.entity

import io.elytra.api.entity.Entity
import io.elytra.api.world.Position
import io.elytra.api.world.World

abstract class ElytraEntity (): Entity{

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

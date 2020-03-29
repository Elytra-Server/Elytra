package io.elytra.api.world

import io.elytra.api.world.enums.Difficulty
import io.elytra.api.world.enums.WorldMode
import io.elytra.api.world.enums.WorldType
import io.elytra.api.world.generator.WorldGenerator

interface World {
	var name: String
	var difficulty: Difficulty
	var type: WorldType
	var mode: WorldMode
	val generator: WorldGenerator

	fun getChunkAt(x: Int, z: Int) : Chunk?

	fun setChunkAt(x: Int, y: Int, z: Int, blockId: Int)
}

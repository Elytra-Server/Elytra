package io.elytra.sdk.world

import io.elytra.api.world.Chunk
import io.elytra.api.world.ChunkManager
import io.elytra.api.world.World
import io.elytra.api.world.enums.Difficulty
import io.elytra.api.world.enums.WorldMode
import io.elytra.api.world.enums.WorldType
import io.elytra.api.world.generator.WorldGenerator
import io.elytra.sdk.world.generator.FlatGenerator

class ElytraWorld(
	override var name: String,
	override var difficulty: Difficulty,
	override var type: WorldType,
	override var mode: WorldMode,
	override val generator: WorldGenerator = FlatGenerator(),

	private val chunkManager: ChunkManager = ElytraChunkManager(10)
) : World {

	override fun getChunkAt(x: Int, z: Int): Chunk? {
		return chunkManager.getChunk(x, z)
	}
}

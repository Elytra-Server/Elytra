package io.elytra.sdk.world.generator

import io.elytra.api.world.Chunk
import io.elytra.api.world.World
import io.elytra.api.world.generator.WorldGenerator
import io.elytra.sdk.world.ElytraChunk


class FlatGenerator : WorldGenerator {
	override fun generate(chunkX: Int, chunkY: Int, world: World): Chunk {
		val chunk = ElytraChunk(chunkX, chunkY)

		for(x in 0..16){
			for(y in 0..128){
				for(z in 0..16){
					world.setChunkAt(x, y, z, 2)
				}
			}
		}

		return chunk
	}

}

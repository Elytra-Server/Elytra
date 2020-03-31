package io.elytra.sdk.world.generator

import io.elytra.api.world.Chunk
import io.elytra.api.world.ChunkColumn
import io.elytra.api.world.World
import io.elytra.api.world.generator.WorldGenerator
import io.elytra.sdk.world.ElytraChunk
import io.elytra.sdk.world.ElytraChunkColumn


class FlatGenerator : WorldGenerator {
	override fun generate(chunkX: Int, chunkY: Int, world: World): ChunkColumn {
		val chunk = ElytraChunkColumn(chunkX, chunkY)

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

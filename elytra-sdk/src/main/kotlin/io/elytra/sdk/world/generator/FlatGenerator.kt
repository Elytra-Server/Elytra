package io.elytra.sdk.world.generator

import io.elytra.api.world.Chunk
import io.elytra.api.world.World
import io.elytra.api.world.generator.WorldGenerator
import io.elytra.api.world.noise.OpenSimplexNoise
import io.elytra.sdk.world.ElytraChunk


class FlatGenerator : WorldGenerator {
	override fun generate(chunkX: Int, chunkY: Int, world: World): Chunk {
		val chunk = ElytraChunk(chunkX, chunkY)
		val noise = OpenSimplexNoise()

		for(x in 0..16){
			for(y in 0..128){
				for(z in 0..16){
					val flatNoise = noise.eval(x / 10.0, z / 10.0)
					val flatNoiseY = (flatNoise * 3.0f + y).toInt()

					//Set chunk block to x, flatNoiseY, z with grass blocks
				}
			}
		}

		return chunk
	}

}

package io.elytra.sdk.world.generators

import io.elytra.api.world.Chunk
import io.elytra.api.world.World
import io.elytra.api.world.generator.WorldGenerator

class CustomGenerator : WorldGenerator {
    override fun generate(chunkX: Int, chunkY: Int, world: World): Chunk {
        var chunk: Chunk? = null

        return chunk!!
    }
}

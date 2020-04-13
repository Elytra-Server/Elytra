package io.elytra.api.world.generator

import io.elytra.api.world.Chunk
import io.elytra.api.world.World

/**
 * Generates and poplutes the world with custom chunk sections
 *
 * @see [Chunk]
 */
interface WorldGenerator {

    fun generate(chunkX: Int, chunkY: Int, world: World): Chunk
}

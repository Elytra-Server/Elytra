package io.elytra.api.world

interface ChunkColumn {
	val x: Int
	val z: Int
	val chunks: Array<Chunk?>
	val biomes: ByteArray
}

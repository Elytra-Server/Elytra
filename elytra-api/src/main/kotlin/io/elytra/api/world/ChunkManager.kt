package io.elytra.api.world

interface ChunkManager {

	val seed: Long

	fun getChunk(chunkX: Int, chunkZ: Int): Chunk?

	fun setChunk(chunkX: Int, chunkZ: Int)

	fun sendChunksComOCaralho()
}

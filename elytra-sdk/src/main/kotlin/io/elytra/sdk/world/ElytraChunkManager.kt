package io.elytra.sdk.world

import io.elytra.api.world.Chunk
import io.elytra.api.world.ChunkManager
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap


class ElytraChunkManager(
	override val seed: Long,
	private val chunks: Long2ObjectOpenHashMap<Chunk> = Long2ObjectOpenHashMap()
) : ChunkManager {

	override fun getChunk(chunkX: Int, chunkZ: Int): Chunk? {
		val index = getChunkHash(chunkX, chunkZ)

		return if(chunks.containsKey(index)) chunks.get(index) else null
	}

	override fun setChunk(chunkX: Int, chunkZ: Int) {
		val index = getChunkHash(chunkX, chunkZ)

		chunks[index] = ElytraChunk(chunkX, chunkZ)
	}

	override fun clear() {
		chunks.clear()
	}

	private fun getChunkHash(x: Int, z: Int): Long {
		return x.toLong() shl 32 or ((z and 0xffffffffL.toInt()).toLong())
	}
}

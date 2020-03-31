package io.elytra.sdk.world

import io.elytra.api.world.Chunk
import io.elytra.api.world.ChunkColumn

const val chunksPerColumn = 16

data class ElytraChunkColumn(
	override val x: Int,
	override val z: Int,
	override val chunks: Array<Chunk?>,
	override val biomes: ByteArray
) : ChunkColumn {

	constructor(x: Int, z: Int) : this(
		x, z,
		arrayOfNulls(16),
		ByteArray(256)
	)

	init {
		require(chunks.size == chunksPerColumn)
		require(biomes.size == 256)
	}

	fun setBlockLight(x: Int, y: Int, z: Int, level: Int) {
		val chunk = getChunk(y)
		chunk.setBlockLight(x, y, z, level)
	}

	fun setSkyLight(x: Int, y: Int, z: Int, level: Int) {
		val chunk = getChunk(y)
		chunk.setBlockLight(x, y, z, level)
	}

	fun setBiome(x: Int, z: Int, biome: Byte) {
		val index = (z shl 4) or x
		biomes[index] = biome
	}

	private fun getChunk(y: Int) : Chunk {
		val index = y / 16
		var chunk = chunks[index]

		if(chunk == null) {
			chunk = ElytraChunk.empty()
			chunks[index] = chunk
		}

		return chunk
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as ElytraChunkColumn

		if (x != other.x) return false
		if (z != other.z) return false
		if (!chunks.contentEquals(other.chunks)) return false
		if (!biomes.contentEquals(other.biomes)) return false

		return true
	}

	override fun hashCode(): Int {
		var result = x
		result = 31 * result + z
		result = 31 * result + chunks.contentHashCode()
		result = 31 * result + biomes.contentHashCode()
		return result
	}
}

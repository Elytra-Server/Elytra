package io.elytra.sdk.world

import io.elytra.api.io.NibbleArray
import io.elytra.api.world.Chunk

private const val width = 16
private const val height = 16
private const val depth = 16
private const val blocksPerChunk = width * height * depth

class ElytraChunk(
	override val blocks: ByteArray,
	override val data: NibbleArray,
	override val blockLight: NibbleArray,
	override val skyLight: NibbleArray
) : Chunk {

	init {
		require(blocks.size == blocksPerChunk)
		require(data.arr.size == blocksPerChunk / 2)
		require(blockLight.arr.size == blocksPerChunk / 2)
		require(skyLight.arr.size == blocksPerChunk / 2)
	}

	override fun setType(x: Int, y: Int, z: Int, type: Int, extraData: Int) {
		val index = getIndex(x, y, z)
		blocks[index] = type.toByte()
		data[index] = extraData
	}

	override fun setBlockLight(x: Int, y: Int, z: Int, level: Int) {
		blockLight[getIndex(x, y, z)] = level
	}

	override fun setSkyLight(x: Int, y: Int, z: Int, level: Int) {
		skyLight[getIndex(x, y, z)] = level
	}

	private fun getIndex(x: Int, y: Int, z: Int) : Int {
		return ((y % 16) * 256) or (z * 16) or x
	}

	companion object {
		fun empty() : Chunk =
			ElytraChunk(ByteArray(blocksPerChunk), NibbleArray(blocksPerChunk), NibbleArray(blocksPerChunk), NibbleArray(blocksPerChunk))
	}
}

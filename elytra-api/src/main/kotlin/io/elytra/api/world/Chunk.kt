package io.elytra.api.world

import io.elytra.api.io.NibbleArray

interface Chunk {

	val blocks: ByteArray
	val data: NibbleArray
	val blockLight: NibbleArray
	val skyLight: NibbleArray

	fun setType(x: Int, y: Int, z: Int, type: Int, metadata: Int)

	fun setBlockLight(x: Int, y: Int, z: Int, level: Int)

	fun setSkyLight(x: Int, y: Int, z: Int, level: Int)
}

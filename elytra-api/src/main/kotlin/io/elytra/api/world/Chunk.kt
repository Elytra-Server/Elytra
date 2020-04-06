package io.elytra.api.world

import io.elytra.api.io.NibbleArray

/**
 * A chunk of size 16x256x16
 */
interface Chunk {

    val skyLight: NibbleArray
    val blockLight: NibbleArray

    fun getBlockAt(x: Int, y: Int, z: Int)

    fun getSkyLight(x: Int, y: Int, z: Int)

    fun getBlockLight(x: Int, y: Int, z: Int)

    fun setSkyLight(x: Int, y: Int, z: Int, skyLight: Int)

    fun setBlockLight(x: Int, y: Int, z: Int, blockLight: Int)

    fun getSectionAt(y: Int): ChunkSection

    fun getBiome(x: Int, y: Int, z: Int)
}

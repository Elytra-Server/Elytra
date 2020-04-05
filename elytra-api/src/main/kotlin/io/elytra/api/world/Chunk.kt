package io.elytra.api.world

interface Chunk {

    fun createColumns()

    fun getChunkAt(y: Int)

    fun getBlockAt(x: Int, y: Int, z: Int)

    fun getSkyLight(x: Int, y: Int, z: Int)

    fun getBlockLight(x: Int, y: Int, z: Int)

    fun setSkyLight(x: Int, y: Int, z: Int, skyLight: Int)

    fun setBlockLight(x: Int, y: Int, z: Int, blockLight: Int)

    fun getBiome(x: Int, z: Int)
}

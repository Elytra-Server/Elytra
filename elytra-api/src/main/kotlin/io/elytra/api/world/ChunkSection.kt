package io.elytra.api.world

import io.elytra.api.io.NibbleArray

/**
 * One of the 16 16x16x16 sections of a Chunk
 */
interface ChunkSection {

    /**
     * The block palette mapping. Maps block ids contained in this chunk section to the ids in the global palette
     */
    val palette: List<Int>

    /**
     * Variable size bit array containing the blocks ids from the local palette.
     * TODO The size of elements in this array is variable, but for is fixed at 8 for testing
     */
    val data: ByteArray

    val skyLight: NibbleArray
    val blockLight: NibbleArray
}

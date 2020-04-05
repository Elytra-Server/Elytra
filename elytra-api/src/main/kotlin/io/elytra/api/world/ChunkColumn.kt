package io.elytra.api.world

import io.elytra.api.io.NibbleArray

/**
 * A single column of a chunk
 */
interface ChunkColumn {

    /**
     * The block palette
     */
    val palette: List<Int>
    val data: ByteArray

    val skyLight: NibbleArray

    val blockLight: NibbleArray
}

package io.elytra.sdk.world

import io.elytra.api.io.NibbleArray
import io.elytra.api.world.ChunkColumn

const val chunksPerColumn = 16

data class ElytraChunkColumn(
    override val palette: List<Int>,
    override val data: ByteArray,
    override val skyLight: NibbleArray,
    override val blockLight: NibbleArray
) : ChunkColumn {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ElytraChunkColumn

        if (palette != other.palette) return false
        if (!data.contentEquals(other.data)) return false
        if (skyLight != other.skyLight) return false
        if (blockLight != other.blockLight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = palette.hashCode()
        result = 31 * result + data.contentHashCode()
        result = 31 * result + skyLight.hashCode()
        result = 31 * result + blockLight.hashCode()
        return result
    }
}

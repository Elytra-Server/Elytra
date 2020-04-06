package io.elytra.sdk.world.strategy

import io.elytra.api.nbt.tags.*
import io.elytra.api.world.*
import java.io.File

class AnvilWorldStrategy : WorldLoadStrategy {
    override fun load(path: String): World {
        TODO("Not implemented yet")
    }

    private fun readRegion(file: File): List<Chunk> {
        TODO("Not implemented yet")
    }

    private fun readChunk(nbt: NbtCompound): Chunk {
        TODO("Not implemented yet")
    }
}

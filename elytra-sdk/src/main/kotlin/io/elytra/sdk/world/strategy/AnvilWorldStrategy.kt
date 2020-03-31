package io.elytra.sdk.world.strategy

import io.elytra.api.world.WorldLoadStrategy
import java.io.File

private const val sectorSize = 4096

data class ChunkEntry(val offset: Int, val paddedSize: Int)

class AnvilWorldStrategy : WorldLoadStrategy {
	override fun load(path: String) {
		TODO("Not yet implemented")
	}

	fun readRegion(file: File){
		
	}

}

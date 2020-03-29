package io.elytra.sdk.world

import io.elytra.api.world.Chunk

data class ElytraChunk(
	override val x: Int,
	override val y: Int
) : Chunk

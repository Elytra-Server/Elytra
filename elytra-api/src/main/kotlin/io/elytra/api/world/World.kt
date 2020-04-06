package io.elytra.api.world

import io.elytra.api.entity.Player
import io.elytra.api.utils.Tickable

interface World : Tickable {
    val name: String
    val spawnPoint: Position

    fun getChunk(x: Int, z: Int): Chunk?

    fun setChunk(x: Int, z: Int, chunk: Chunk)

    fun teleport(player: Player, position: Position)
}

package io.elytra.sdk.world

import io.elytra.api.entity.Player
import io.elytra.api.world.Chunk
import io.elytra.api.world.Position
import io.elytra.api.world.World

class ElytraWorld(
    override val spawnPoint: Position,
    private val chunks: MutableMap<Long, Chunk> = mutableMapOf(),
    override val name: String = ""
) : World {

    override fun getChunk(x: Int, z: Int) = chunks[(x * 4000000 + z).toLong()] ?: ElytraChunk()

    override fun setChunk(x: Int, z: Int, chunk: Chunk) {
        this.chunks[(x * 4000000 + z).toLong()] = chunk
    }

    override fun teleport(player: Player, position: Position) {
        TODO("Not yet implemented")
    }

    override fun tick() {
        TODO("Not yet implemented")
    }
}

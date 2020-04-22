package io.elytra.sdk.world

import io.elytra.api.entity.player.Player
import io.elytra.api.world.Chunk
import io.elytra.api.world.Position
import io.elytra.api.world.World

const val loadFactor = 4000000

class ElytraWorld(
    override val spawnPoint: Position,
    private val chunks: MutableMap<Long, Chunk> = mutableMapOf(),
    override val name: String = ""
) : World {

    override fun getChunkAt(x: Int, z: Int) =
        chunks[(x * loadFactor + z).toLong()] ?: ElytraChunk()

    override fun getChunkAt(position: Position): Chunk? =
        chunks[(position.x * loadFactor + position.z).toLong()] ?: ElytraChunk()

    override fun setChunkAt(x: Int, z: Int, chunk: Chunk) {
        this.chunks[(x * loadFactor + z).toLong()] = chunk
    }

    override fun teleport(player: Player, position: Position) {
        TODO("Not yet implemented")
    }

    override fun tick() {
        TODO("Not yet implemented")
    }
}

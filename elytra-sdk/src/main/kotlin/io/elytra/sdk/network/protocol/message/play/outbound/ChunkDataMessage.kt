package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message
import io.elytra.sdk.world.ElytraChunk

data class ChunkDataMessage(
    val x: Int,
    val z: Int,
    val chunk: ElytraChunk
) : Message

package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message
import io.elytra.api.utils.Asyncable

data class UnloadChunkMessage(
    val chunkX: Int,
    val chunkY: Int
) : Asyncable, Message

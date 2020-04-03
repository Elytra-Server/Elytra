package io.elytra.sdk.network.protocol.message.play

import com.flowpowered.network.Message
import io.netty.buffer.ByteBuf

data class ChunkDataMessage(
    val x: Int,
    val z: Int,
    val data: ByteBuf
) : Message

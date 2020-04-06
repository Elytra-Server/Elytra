package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message

data class SpawnPositionMessage(
    val x: Int = 0,
    val y: Int = 0,
    val z: Int = 0
) : Message

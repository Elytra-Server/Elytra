package io.elytra.sdk.network.protocol.message.play

import com.flowpowered.network.Message

data class PlayerRotationMessage(
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float,
    val pitch: Float,
    val flags: Byte = 0,
    val teleportId: Int = 0
) : Message

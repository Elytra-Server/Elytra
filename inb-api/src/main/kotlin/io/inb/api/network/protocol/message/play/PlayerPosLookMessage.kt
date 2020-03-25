package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message

data class PlayerPosLookMessage(
	val x: Double,val y: Double,val z: Double,val yaw: Float,
	val pitch: Float,val flags: Byte,val teleportId: Int
) : Message



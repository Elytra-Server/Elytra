package io.elytra.sdk.network.protocol.message.play

import com.flowpowered.network.Message

data class EntityStatusMessage(
	val entityId: Int,
	val logicOpcode: Byte
) : Message



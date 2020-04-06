package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message

data class EntityStatusMessage(
    val entityId: Int,
    val logicOpcode: Byte
) : Message

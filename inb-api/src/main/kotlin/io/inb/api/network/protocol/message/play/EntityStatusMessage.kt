package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message

data class EntityStatusMessage(val entityId: Int,val logicOpcode: Byte) : Message



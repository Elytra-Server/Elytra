package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message

data class ConfirmTeleportMessage(
	val telportId: Int
) : Message

package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message

data class HeldItemChangeMessage(val heldItemHotbarIndex: Int) : Message

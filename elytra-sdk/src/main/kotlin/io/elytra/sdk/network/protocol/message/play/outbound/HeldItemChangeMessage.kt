package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message

data class HeldItemChangeMessage(val heldItemHotbarIndex: Int) : Message

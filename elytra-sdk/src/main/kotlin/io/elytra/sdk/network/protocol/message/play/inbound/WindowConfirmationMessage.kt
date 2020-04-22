package io.elytra.sdk.network.protocol.message.play.inbound

import com.flowpowered.network.Message

data class WindowConfirmationMessage(
    val windowId: Byte,
    val actionNumber: Short,
    val accepted: Boolean
) : Message

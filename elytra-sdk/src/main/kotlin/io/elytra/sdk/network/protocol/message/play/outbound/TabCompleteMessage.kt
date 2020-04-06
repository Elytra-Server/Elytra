package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message

data class TabCompleteMessage(
    val transactionId: Int,
    val text: String
) : Message

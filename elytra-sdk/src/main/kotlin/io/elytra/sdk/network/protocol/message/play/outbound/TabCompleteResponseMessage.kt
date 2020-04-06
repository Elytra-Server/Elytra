package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message
import io.elytra.api.command.TabCompletion

data class TabCompleteResponseMessage(
    val transactionId: Int = 0,
    val startIndex: Int = 0,
    val textLength: Int = 0,
    val completions: List<TabCompletion> = listOf()
) : Message

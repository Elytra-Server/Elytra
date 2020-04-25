package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message
import io.elytra.api.chat.TextComponent

data class TabCompleteResponseMessage(
    val transactionId: Int = 0,
    val startIndex: Int = 0,
    val textLength: Int = 0,
    val completions: List<TabCompletion> = listOf()
) : Message {

    /**
     * Represents a completion when clicked on the TAB key
     * on the chat
     *
     */
    data class TabCompletion(
        /**
         * What should be matched in order to show the completion
         */
        val match: String,
        /**
         * On hover tooltip
         */
        val tooltip: TextComponent?
    )
}

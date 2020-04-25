package io.elytra.api.command

import io.elytra.api.chat.TextComponent

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
    val tooltip: TextComponent
)

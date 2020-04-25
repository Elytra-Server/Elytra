package io.elytra.api.command

import io.elytra.api.chat.TextComponent

data class TabCompletion(
    /** The index where the completion begins. Default it's the beginning of the last word */
    var startIndex: Int = 0,
    /** The length of the text to replace after the [startIndex]. Default it's the whole word. */
    var textLength: Int = 0,
    /** List of completions to show to the client */
    var completions: MutableList<Completion> = mutableListOf()
) {
    /** Represents a completion to show when the client presses Tab. */
    data class Completion(
        /** The match found for completing the string */
        var match: String,
        /** The tooltip to show when hovering the completion */
        var tooltip: TextComponent?
    ) {
        constructor(match: String) : this(match, match)
        constructor(match: String, tooltip: String) : this(match, TextComponent(tooltip))
    }
}

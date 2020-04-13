package io.elytra.api.chat

/**
 * Represents a chat message
 *
 * @see [Chat formatting](https://wiki.vg/Chat)
 */
data class TextComponent(
    var text: String,
    var clickEvent: ClickEvent? = null,
    var hoverEvent: HoverEvent? = null,
    val extra: MutableList<Extra>? = null
) {
    data class Extra(
        val text: String,
        val bold: Boolean? = false,
        val italic: Boolean? = false,
        val underlined: Boolean? = false,
        val strikethrough: Boolean? = false,
        val obfuscated: Boolean? = false
    )
}

package io.elytra.api.chat

/**
 * DSL to initialize a new [TextComponent] with the base [text].
 */
fun TextComponent(text: String, op: TextComponent.() -> Unit) = TextComponent(text).apply(op)

/**
 * TextComponent provides an easy way to build messages with custom
 * hover and click events without resorting to raw json.
 */
class TextComponent(var text: String) : JsonComponent {
    var clickEvent: ClickEvent? = null
    var hoverEvent: HoverEvent? = null
    var extra: MutableList<TextComponent>? = null

    // Text formatting attributes
    var color: String? = null
    var bold: Boolean = false
    var italic: Boolean = false
    var underlined: Boolean = false
    var strikeThrough: Boolean = false
    var obfuscated: Boolean = false

    /** Add a click event to run the [action] with the given [value]. */
    fun clickEvent(action: ClickEvent.Action, value: String) {
        this.clickEvent = ClickEvent(action, value)
    }

    /** Add a hover event to show the [action] with the given [value]. */
    fun hoverEvent(action: HoverEvent.Action, value: JsonComponent) {
        this.hoverEvent = HoverEvent(action, value)
    }

    /**
     * DSL for adding extra components to this components
     *
     * @see addExtra
     */
    fun addExtra(text: String, op: TextComponent.() -> Unit = {}) = addExtra(TextComponent(text, op))

    /**
     * Add an extra [component] to this component.
     *
     * Extra components act as child components, this means that
     * they inherit the parents format and click and hover events.
     */
    fun addExtra(component: TextComponent): TextComponent {
        if (extra == null) {
            extra = mutableListOf()
        }

        extra!!.add(component)

        return this
    }

    /** Check if the component has any formatting. */
    fun hasFormatting(): Boolean {
        return color != null || bold || italic || underlined || strikeThrough || obfuscated
    }

    override fun toJson(buff: Appendable) {
        if (!hasFormatting() && clickEvent == null && hoverEvent == null && extra == null) {
            buff.append('"').append(text).append('"')
            return
        }

        buff.append('{')
        buff.append("\"text\":\"").append(text).append('"')

        if (clickEvent != null) {
            buff.append(',').append("\"clickEvent\":")
            clickEvent!!.toJson(buff)
        }
        if (hoverEvent != null) {
            buff.append(',').append("\"hoverEvent\":")
            hoverEvent!!.toJson(buff)
        }
        if (color != null) {
            buff.append(",\"color\":\"")
                .append(ChatColor.values().firstOrNull { it.color == color || it.minecraft == color }?.colorName
                    ?: color)
                .append('"')
        }
        if (bold) buff.append(",\"bold\":true")
        if (italic) buff.append(",\"italic\":true")
        if (underlined) buff.append(",\"underlined\":true")
        if (strikeThrough) buff.append(",\"strikethrough\":true")
        if (obfuscated) buff.append(",\"obfuscated\":true")

        if (!extra.isNullOrEmpty()) {
            buff.append(",\"extra\":[")

            val extraIt = extra!!.iterator()
            buff.append(extraIt.next().toString())
            while (extraIt.hasNext()) {
                buff.append(',').append(extraIt.next().toString())
            }

            buff.append(']')
        }

        buff.append('}')
    }

    override fun toString(): String = toJson()
}

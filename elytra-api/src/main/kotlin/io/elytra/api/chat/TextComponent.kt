package io.elytra.api.chat

class TextComponent(var text: String) : JsonComponent {
    var clickEvent: ClickEvent? = null
    var hoverEvent: HoverEvent? = null
    var extra: MutableList<TextComponent>? = null

    var color: String? = null
    var bold: Boolean = false
    var italic: Boolean = false
    var underlined: Boolean = false
    var strikeThrough: Boolean = false
    var obfuscated: Boolean = false

    fun addExtra(text: String): TextComponent = addExtra(TextComponent(text))

    fun addExtra(component: TextComponent): TextComponent {
        if (extra == null) {
            extra = mutableListOf()
        }

        extra!!.add(component)

        return this
    }

    override fun toJson(buff: Appendable) {
        if (!hasFormatting() && clickEvent == null && hoverEvent == null && extra == null) {
            buff.append("\"$text\"")
            return
        }

        buff.append('{')
        buff.append("\"text\":\"")
        ChatColor.replaceColors(text, buff)
        buff.append('"')
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

    fun hasFormatting(): Boolean {
        return color != null || bold || italic || underlined || strikeThrough || obfuscated
    }
}

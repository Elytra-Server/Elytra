package io.elytra.api.chat

/**
 * Allows for a tooltip to be displayed when the player hovers their mouse over text.
 */
data class HoverEvent(val action: Action, val value: JsonComponent) : JsonComponent {

    override fun toJson(buff: Appendable) {
        buff.append('{')
        buff.append("\"action\":\"").append(action.actionName).append('"')
        buff.append(",\"value\":").append(value.toJson())
        buff.append('}')
    }

    override fun toString(): String = toJson()

    enum class Action(val actionName: String) {
        /**
         * Shows raw JSON text.
         */
        SHOW_TEXT("show_text"),

        /**
         * Shows the tooltip of an item that can have NBT tags.
         */
        SHOW_ITEM("show_item"),

        /**
         * Shows an entity's name, possibly its type, and its UUID.
         */
        SHOW_ENTITY("show_entity")
    }
}

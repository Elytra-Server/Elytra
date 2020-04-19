package io.elytra.api.chat

/**
 * Allows for events to occur when the player clicks on text.
 */
data class ClickEvent(val action: Action, val value: String) : JsonComponent {

    override fun toJson(buff: Appendable) {
        buff.append('{')
        buff.append("\"action\":\"").append(action.actionName).append('"')
        buff.append(",\"value\":\"").append(value).append('"')
        buff.append('}')
    }

    override fun toString(): String = toJson()

    enum class Action(val actionName: String) {
        /**
         * Opens value as a URL in the player's default web browser.
         */
        OPEN_URL("open_url"),

        /**
         * Opens the value file on the user's computer.
         *
         * This is used in messages automatically generated
         * by the game (e.g. on taking a screenshot) and
         * cannot be used in commands or signs.
         */
        OPEN_FILE("open_file"),

        /**
         * Has value entered in chat as though the player typed it themselves.
         * This can be used to run commands, provided the player has the required permissions.
         */
        RUN_COMMAND("run_command"),

        /**
         * similar to "run_command" but it cannot be used in a written
         * book, the text appears only in the player's chat input
         * and it is not automatically entered.
         *
         * Unlike insertion, this replaces the existing contents of the chat input
         */
        SUGGEST_COMMAND("suggest_command"),

        /**
         * Changes to page value if that page exists.
         *
         * Note: Can be used only in written books.
         */
        CHANGE_PAGE("change_page"),

        /**
         * Copy the value to the clipboard.
         */
        COPY_TO_CLIPBOARD("copy_to_clipboard")
    }
}

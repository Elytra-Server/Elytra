package io.elytra.api.chat

/**
 * Represents a bunch of ansi colors
 */
enum class ChatColor(val code: String, val colorName: String, val ansi: String) {
    RESET("r", "reset", "\u001B[0m"),
    BLACK("0", "black", "\u001B[30m"),
    BLUE("1", "dark_blue", "\u001B[34m"),
    GREEN("2", "dark_green", "\u001B[32m"),
    CYAN("3", "dark_aqua", "\u001B[36m"),
    RED("4", "dark_red", "\u001B[31m"),
    PURPLE("5", "dark_purple", "\u001B[35m"),
    YELLOW("6", "gold", "\u001B[33m"),
    LIGHT_GRAY("7", "gray", "\u001B[37m"),
    GRAY("8", "dark_gray", "\u001B[90m"),
    LIGHT_BLUE("9", "blue", "\u001B[94m"),
    LIGHT_GREEN("a", "green", "\u001B[92m"),
    LIGHT_CYAN("b", "aqua", "\u001B[96m"),
    LIGHT_RED("c", "red", "\u001B[91m"),
    LIGHT_PURPLE("d", "light_purple", "\u001B[95m"),
    LIGHT_YELLOW("e", "yellow", "\u001B[93m"),
    WHITE("f", "white", "\u001B[97m");

    val color by lazy { COLOR_CHAR + code }
    val minecraft by lazy { MINECRAFT_COLOR_CHAR + code }

    companion object {
        const val COLOR_CHAR = '&'
        const val MINECRAFT_COLOR_CHAR = '§'
        /**
         * Replace the [color] with the [toAnsi] code and builts
         * into a string
         *
         * @return string with the replaced colors to the ansi codes
         */
        fun replaceColors(text: String, toAnsi: Boolean = false): String {
            val buff = StringBuilder()
            replaceColors(text, buff, toAnsi)
            return buff.toString()
        }

        fun replaceColors(text: String, appender: Appendable, toAnsi: Boolean = false) {
            val replaces = values()
            val toIgnore = Array(replaces.size) { false }

            // The point to start searching for a match to replace
            var start = 0
            // The index of the value to replace in the text
            var textIndex: Int
            // The index of the replacement in the [replaces]
            var replaceIndex: Int
            do {
                textIndex = -1
                replaceIndex = -1
                replaces.forEachIndexed { i, replace ->
                    // Get the next match to replace
                    if (toIgnore[i]) {
                        // We already checked this value and it wasn't found in the text, so skip it
                        return@forEachIndexed
                    }

                    val index = text.indexOf(replace.color, start)
                    if (index == -1) {
                        toIgnore[i] = true
                    } else {
                        if (textIndex == -1 || index < textIndex) {
                            // Found a match, save the indexes
                            textIndex = index
                            replaceIndex = i
                        }
                    }
                }

                if (textIndex == -1) {
                    // No more matches found
                    break
                }

                // Add everything until the match
                for (i in start until textIndex) {
                    appender.append(text[i])
                }

                // Place the replacement
                if (toAnsi)
                    appender.append(replaces[replaceIndex].ansi)
                else
                    appender.append(replaces[replaceIndex].minecraft)

                // Increment the next starting point
                // 2 is the text length of the color, e.g. `&a`
                start = textIndex + 2
            } while (textIndex != -1)

            for (i in start until text.length) {
                appender.append(text[i])
            }
        }
    }
}

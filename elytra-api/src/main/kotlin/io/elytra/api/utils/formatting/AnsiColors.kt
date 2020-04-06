package io.elytra.api.utils.formatting

enum class AnsiColors(val ansi: String, private val color: String) {
    RESET("\u001B[0m", "&r"),
    BLACK("\u001B[30m", "&0"),
    BLUE("\u001B[34m", "&1"),
    GREEN("\u001B[32m", "&2"),
    CYAN("\u001B[36m", "&3"),
    RED("\u001B[31m", "&4"),
    PURPLE("\u001B[35m", "&5"),
    YELLOW("\u001B[33m", "&6"),
    LIGHT_GRAY("\u001B[37m", "&7"),
    GRAY("\u001B[90m", "&8"),
    LIGHT_BLUE("\u001B[94m", "&9"),
    LIGHT_GREEN("\u001B[92m", "&a"),
    LIGHT_CYAN("\u001B[96m", "&b"),
    LIGHT_RED("\u001B[91m", "&c"),
    LIGHT_PURPLE("\u001B[95m", "&d"),
    LIGHT_YELLOW("\u001B[93m", "&e"),
    WHITE("\u001B[97m", "&f");

    companion object {
        fun replaceColors(text: String): String {
            val replaces = values()
            val toIgnore = Array(replaces.size) { false }

            val builder = StringBuilder()
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
                    builder.append(text[i])
                }

                // Place the replacement
                builder.append(replaces[replaceIndex].ansi)

                // Increment the next starting point
                // 2 is the text length of the color, e.g. `&a`
                start = textIndex + 2
            } while (textIndex != -1)

            for (i in start until text.length) {
                builder.append(text[i])
            }

            return builder.toString()
        }
    }
}

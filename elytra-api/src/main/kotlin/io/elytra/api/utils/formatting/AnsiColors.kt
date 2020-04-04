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
        fun replaceByColor(text: String): String {
            var message = text.replace("§", "&")

            values().forEach {
                message = message.replace(it.color, it.ansi)
            }

            return message
        }
    }
}

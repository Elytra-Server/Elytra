package io.elytra.api.utils.formatting

enum class AnsiColors(val ansi: String, private val color: String) {
	RESET("\u001B[0m", "&r"),
	BLACK("\u001B[30m", "&0"),
	RED("\u001B[31m", "&c"),
	GREEN("\u001B[32m", "&2"),
	YELLOW("\u001B[33m", "&e"),
	BLUE("\u001B[34m", "&9"),
	PURPLE("\u001B[35m", "&5"),
	CYAN("\u001B[36m", "&b"),
	WHITE("\u001B[37m", "&f");

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

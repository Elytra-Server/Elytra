package io.elytra.api.chat


data class ChatComponent(
	val text: String,
	var clickEvent: ClickEvent? = null,
	var hoverEvent: HoverEvent? = null,
	val extra: MutableList<Extra>? = null
) {
	data class Extra(val text: String, val formatOption: TextFormat)
}

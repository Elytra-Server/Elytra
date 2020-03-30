package io.elytra.api.chat


data class ChatComponent(
	val text: String,
	val clickEvent: ClickEvent? = null,
	val extra: MutableList<Extra>? = null
) {
	data class Extra(val text: String, val formatOption: TextFormat)
}

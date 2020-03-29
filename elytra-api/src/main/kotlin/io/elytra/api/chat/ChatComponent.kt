package io.elytra.api.chat


data class ChatComponent(
	val text: String,
	val extra: MutableList<Extra>? = null
) {
	data class Extra(val text: String, val formatOption: TextFormat)
}

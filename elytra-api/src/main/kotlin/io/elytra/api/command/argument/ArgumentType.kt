package io.elytra.api.command.argument

interface ArgumentType<T> {

	fun parse(stringArgumentList: List<String>, index: Int): T?

}

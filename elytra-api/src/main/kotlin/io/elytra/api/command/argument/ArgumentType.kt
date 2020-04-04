package io.elytra.api.command.argument

interface ArgumentType<T> {

	fun parse(values: List<String>, indexNumber: Int): T?

}

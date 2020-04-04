package io.elytra.api.command.argument

interface Argument<T> {

	fun getValue() : T

	val context: ArgumentContext<T>

}

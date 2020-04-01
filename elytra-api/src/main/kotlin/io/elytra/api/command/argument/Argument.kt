package io.elytra.api.command.argument

interface Argument<T> {

	val value: T

	val context: ArgumentContext<T>

}

package io.elytra.api.command.argument

interface ArgumentList {

	fun <T> getArgument(argumentContextName: String): Argument<T>?

	fun <T> getArgument(argumentIndex: Int): Argument<T>?

	fun <T> getUnrequiredArgument(argumentContextName: String): Argument<T>?

	fun <T> getUnrequiredArgument(argumentIndex: Int): Argument<T>?

	fun <T> getValue(argumentContextName: String): T

	fun <T> getValue(argumentIndex: Int): T

	fun <T> getUnrequiredValue(argumentContextName: String): T?

	fun <T> getUnrequiredValue(argumentIndex: Int): T?

	fun getAll(): List<Argument<*>>

}

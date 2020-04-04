package io.elytra.sdk.command.argument

import io.elytra.api.command.argument.Argument
import io.elytra.api.command.argument.ArgumentList

class ArgumentListImpl : ArgumentList {

	private val map: MutableMap<String, Argument<Any>> = HashMap()
	private val list: MutableList<Argument<Any>> = ArrayList()

	override fun <T> getArgument(argumentContextName: String): Argument<T> = map[argumentContextName] as Argument<T>

	override fun <T> getArgument(argumentIndex: Int): Argument<T> = list[argumentIndex] as Argument<T>

	override fun <T> getUnrequiredArgument(argumentContextName: String): Argument<T>? = map[argumentContextName] as Argument<T>?

	override fun <T> getUnrequiredArgument(argumentIndex: Int): Argument<T>? = list[argumentIndex] as Argument<T>?

	override fun <T> getValue(argumentContextName: String): T = getArgument<T>(argumentContextName).value

	override fun <T> getValue(argumentIndex: Int): T = getArgument<T>(argumentIndex).value

	override fun <T> getUnrequiredValue(argumentContextName: String): T? = getUnrequiredArgument<T>(argumentContextName)?.value

	override fun <T> getUnrequiredValue(argumentIndex: Int): T? = getUnrequiredArgument<T>(argumentIndex)?.value

	override fun getAll(): List<Argument<*>> = list

	fun add(argument: Argument<Any>) {
		map[argument.context.name] = argument
		list.add(argument)
	}

	operator fun plusAssign(argument: Argument<Any>) {
		add(argument)
	}

}

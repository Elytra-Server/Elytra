package io.elytra.api.command

import io.elytra.api.command.argument.ArgumentContext
import io.elytra.api.command.argument.ArgumentType

abstract class BaseCommand(override val name: String) : Command {

	private val argumentList: MutableList<ArgumentContext<Any>> = ArrayList()

	override fun getArguments(): List<ArgumentContext<Any>> = argumentList

	final override fun addArgument(name: String, type: ArgumentType<*>, required: Boolean) {
		argumentList += object : ArgumentContext<Any> {
			override val name: String = name
			override val type: ArgumentType<Any> = type as ArgumentType<Any>
			override val required: Boolean = required
		}
	}
}

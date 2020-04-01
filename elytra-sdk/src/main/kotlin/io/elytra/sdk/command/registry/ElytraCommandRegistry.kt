package io.elytra.sdk.command.registry

import io.elytra.api.command.Command
import io.elytra.api.command.registry.CommandRegistry

class ElytraCommandRegistry : CommandRegistry {

	private val commandRegistry: MutableMap<String, Command> = HashMap()

	override fun register(command: Command) {
		val commandName = command.name
		if (commandRegistry.containsKey(commandName)) {
			throw CommandAlreadyRegistered(commandName)
		}
		commandRegistry[commandName] = command
	}

	override fun getCommandByName(commandName: String): Command? {
		return commandRegistry[commandName]
	}

	class CommandAlreadyRegistered(commandName: String) : Exception("$commandName is already registered")

}

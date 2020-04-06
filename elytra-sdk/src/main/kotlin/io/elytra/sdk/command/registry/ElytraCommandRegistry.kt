package io.elytra.sdk.command.registry

import io.elytra.api.command.Command
import io.elytra.api.command.registry.CommandRegistry
import io.elytra.sdk.commands.GamemodeCommand
import io.elytra.sdk.commands.TestCommand

class ElytraCommandRegistry : CommandRegistry {

    private val commandRegistry: MutableMap<String, Command> = HashMap()

    init {
        registerDefaults()
    }

    @Synchronized
    override fun register(command: Command) {
        val commandName = command.name
        if (commandRegistry.containsKey(commandName)) {
            throw CommandAlreadyRegistered(commandName)
        }

        commandRegistry[commandName] = command
        // TODO("Validate command: arguments, name, etc.")
    }

    @Synchronized
    override fun getCommandByName(commandName: String): Command? {
        return commandRegistry[commandName]
    }

    private fun registerDefaults() {
        register(TestCommand())
        register(GamemodeCommand())
    }

    class CommandAlreadyRegistered(commandName: String) : Exception("$commandName is already registered")
}

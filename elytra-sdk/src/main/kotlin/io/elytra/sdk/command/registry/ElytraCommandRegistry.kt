package io.elytra.sdk.command.registry

import io.elytra.api.command.Command
import io.elytra.api.command.CommandInfo
import io.elytra.api.command.registry.CommandRegistry
import io.elytra.sdk.commands.GamemodeCommand
import io.elytra.sdk.commands.TestCommand
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

class ElytraCommandRegistry : CommandRegistry {

    private val commandRegistry: MutableMap<String, Command> = HashMap()

    init {
        registerDefaults()
    }

    @Synchronized
    override fun register(command: Command) {
        val commandClazz: KClass<Command> = command::class as KClass<Command>
        val commandInfo = commandClazz.findAnnotation<CommandInfo>()

        require(commandInfo != null) { "Elytra command must have a @CommandInfo" }

        val commandName = commandInfo.label

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

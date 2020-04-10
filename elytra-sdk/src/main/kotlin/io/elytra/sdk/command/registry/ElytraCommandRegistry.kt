package io.elytra.sdk.command.registry

import io.elytra.api.command.Command
import io.elytra.api.command.ElytraCommand
import io.elytra.api.command.annotations.CommandArgument
import io.elytra.api.command.annotations.CommandSpec
import io.elytra.api.command.registry.CommandRegistry
import io.elytra.sdk.commands.ChunkCommand
import io.elytra.sdk.commands.DebugCommand
import io.elytra.sdk.commands.GamemodeCommand
import io.elytra.sdk.commands.TestCommand
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.findAnnotation

class ElytraCommandRegistry : CommandRegistry {

    private val commandRegistry: MutableMap<String, Command> = HashMap()

    init {
        registerDefaults()
    }

    @Synchronized
    override fun register(command: Command) {
        val commandClazz: KClass<Command> = command::class as KClass<Command>
        val commandInfo = commandClazz.findAnnotation<CommandSpec>()

        val elytraCommand = command as ElytraCommand

        val executeFun = commandClazz.declaredFunctions.first { it.name.toLowerCase() == "execute" }

        for (annotation in executeFun.annotations) {
            if (annotation is CommandArgument) {
                elytraCommand.addArgument(annotation)
            }
        }

        require(commandInfo != null) { "Elytra command must have a @CommandInfo" }

        val commandName = commandInfo.label

        if (commandRegistry.containsKey(commandName)) {
            throw CommandAlreadyRegistered(commandName)
        }

        commandRegistry[commandName] = command
        println("Command $commandName has been registered.")
        // TODO("Validate command: arguments, name, etc.")
    }

    @Synchronized
    override fun getCommandByName(commandName: String): Command? {
        return commandRegistry[commandName]
    }

    @Synchronized
    override fun disableCommand(commandName: String) {
        commandRegistry.remove(commandName)
    }

    private fun registerDefaults() {
        register(TestCommand())
        register(GamemodeCommand())
        register(DebugCommand())
        register(ChunkCommand())
    }

    class CommandAlreadyRegistered(commandName: String) : Exception("$commandName is already registered")
}

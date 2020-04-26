package io.elytra.api.command.registry

import io.elytra.api.command.Command

/**
 * Represents a command manager that providers read/write operations
 * for the class [Command]
 */
interface CommandRegistry {

    /**
     * Registers a command on a map in order to became usable
     */
    fun register(command: Command)

    /**
     * Returns all available commands.
     */
    fun getCommands(): Collection<Command>

    /**
     * Returns a [Command] by his [name].
     */
    fun getCommandByName(name: String): Command?

    /**
     * Returns a [Command] by one of his [alias].
     */
    fun getCommandByAlias(alias: String): Command?

    /**
     * Removes a registered command by his [name].
     */
    fun disableCommand(name: String)
}

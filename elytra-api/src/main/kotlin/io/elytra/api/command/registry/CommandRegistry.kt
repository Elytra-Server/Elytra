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
     * Returns a command by his name
     *
     * @return [Command] command
     */
    fun getCommandByName(commandName: String): Command?

    /**
     * Returns a command by one of his alias
     *
     * @return [Command] command
     */
    fun getCommandByAlias(alias: String): Command?

    /**
     * Removes a registered command by his name
     */
    fun disableCommand(commandName: String)
}

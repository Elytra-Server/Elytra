package io.elytra.api.command.registry

import io.elytra.api.command.Command

interface CommandRegistry {

    fun register(command: Command)

    fun getCommandByName(commandName: String): Command?
}

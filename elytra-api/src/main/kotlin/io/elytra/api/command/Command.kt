package io.elytra.api.command

import io.elytra.api.command.argument.ArgumentContext

interface Command {

    val name: String

    val executor: CommandExecutor

    val arguments: List<ArgumentContext<Any>>
}

package io.elytra.api.command

import io.elytra.api.command.annotations.CommandArgument
import io.elytra.api.command.argument.ArgumentList

interface Command {

    fun getArguments(): List<CommandArgument>

    fun execute(sender: CommandSender, arguments: ArgumentList)
}

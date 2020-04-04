package io.elytra.api.command

import io.elytra.api.command.argument.ArgumentContext
import io.elytra.api.command.argument.ArgumentList
import io.elytra.api.command.argument.ArgumentType
import io.elytra.api.entity.Player

interface Command {

    val name: String

    fun getArguments(): List<ArgumentContext<Any>>

    fun addArgument(name: String, type: ArgumentType<*>, required: Boolean = true)

    fun execute(player: Player, arguments: ArgumentList)
}

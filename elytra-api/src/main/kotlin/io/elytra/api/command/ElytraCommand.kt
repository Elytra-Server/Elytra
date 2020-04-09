package io.elytra.api.command

import io.elytra.api.command.annotations.CommandArgument

/**
 * Representation of a server command
 *
 * All commands must extends this in order to be functional
 */
abstract class ElytraCommand : Command {

    private val argumentList: MutableList<CommandArgument> = ArrayList()

    override fun getArguments(): List<CommandArgument> = argumentList

    fun addArgument(commandArgument: CommandArgument) = argumentList.add(commandArgument)
}

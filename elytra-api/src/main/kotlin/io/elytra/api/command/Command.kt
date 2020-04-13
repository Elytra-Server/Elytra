package io.elytra.api.command

import io.elytra.api.command.annotations.CommandArgument
import io.elytra.api.command.argument.ArgumentList

/**
 * Represents a input command
 */
interface Command {

    /**
     * All arguments registered on the command using the annotation
     * [CommandArgument]
     *
     * @return [List] of [CommandArgument]
     */
    fun getArguments(): List<CommandArgument>

    /**
     * Executor that is dispatched when the command is entered by the
     * issuer
     */
    fun execute(issuer: CommandIssuer, arguments: ArgumentList)
}

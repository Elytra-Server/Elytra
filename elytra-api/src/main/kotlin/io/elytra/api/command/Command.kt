package io.elytra.api.command

import io.elytra.api.command.annotations.CommandArgument
import io.elytra.api.command.argument.ArgumentList

/**
 * Represents a input command
 */
interface Command {

    /**
     * Command label
     *
     * @return [String]
     */
    val label: String

    /**
     * All arguments registered on the command using the annotation
     * [CommandArgument]
     *
     * @return [List] of [CommandArgument]
     */
    val argumentList: List<CommandArgument>

    /**
     * All aliases the command has.
     *
     * @return [List] of [String]
     */
    val aliases: List<String>

    /**
     * Executor that is dispatched when the command is entered by the
     * issuer
     */
    fun execute(issuer: CommandIssuer, arguments: ArgumentList)
}

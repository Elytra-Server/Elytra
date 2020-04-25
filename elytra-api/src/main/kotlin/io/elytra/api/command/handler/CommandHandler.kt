package io.elytra.api.command.handler

import io.elytra.api.command.CommandIssuer

/**
 * Provides a way to handle the commands
 */
interface CommandHandler {

    fun handle(issuer: CommandIssuer, message: String)

    /**
     * Get the tab completion for the given command [message].
     *
     * The message starts with a `/` followed by the command string.
     */
    fun handleTabComplete(issuer: CommandIssuer, message: String): Set<String>
}

package io.elytra.api.command.handler

import io.elytra.api.command.CommandIssuer

/**
 * Provides a way to handle the commands
 */
interface CommandHandler {

    fun handle(issuer: CommandIssuer, message: String)
}

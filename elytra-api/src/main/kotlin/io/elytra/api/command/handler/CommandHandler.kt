package io.elytra.api.command.handler

import io.elytra.api.command.CommandSender

interface CommandHandler {

    fun handle(sender: CommandSender, message: String)
}

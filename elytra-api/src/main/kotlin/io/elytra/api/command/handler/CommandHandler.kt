package io.elytra.api.command.handler

import io.elytra.api.entity.Player

interface CommandHandler {

    fun handle(player: Player, string: String)
}

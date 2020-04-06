package io.elytra.sdk.commands

import io.elytra.api.command.BaseCommand
import io.elytra.api.command.CommandSender
import io.elytra.api.command.argument.ArgumentList
import io.elytra.api.command.argument.ArgumentTypes
import io.elytra.api.entity.Player
import io.elytra.api.world.enums.GameMode
import io.elytra.sdk.utils.localeMessage

class GamemodeCommand : BaseCommand("gamemode") {

    init {
        addArgument("type", ArgumentTypes.STRING)
    }

    override fun execute(sender: CommandSender, arguments: ArgumentList) {
        val player = sender as Player
        val arg = arguments.getValue<String>(0)
        val gamemode = GameMode.valueOf(arg.toUpperCase())

        player.gamemode = gamemode

        player.localeMessage("command.gamemode.success") {
            with("gamemode", gamemode.name.toLowerCase())
        }
    }
}

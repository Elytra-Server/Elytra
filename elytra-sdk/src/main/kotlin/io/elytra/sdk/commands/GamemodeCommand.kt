package io.elytra.sdk.commands

import io.elytra.api.command.CommandSender
import io.elytra.api.command.ElytraCommand
import io.elytra.api.command.annotations.CommandArgument
import io.elytra.api.command.annotations.CommandSpec
import io.elytra.api.command.argument.ArgumentList
import io.elytra.api.command.argument.ArgumentTypes
import io.elytra.api.entity.Player
import io.elytra.api.world.enums.GameMode
import io.elytra.sdk.utils.localeMessage

@CommandSpec(label = "gamemode")
class GamemodeCommand : ElytraCommand() {

    @CommandArgument("type", ArgumentTypes.Default::class)
    override fun execute(sender: CommandSender, arguments: ArgumentList) {
        val player = sender as Player
        val arg = arguments.getValue<String>(0)

        val gamemode = GameMode.get(arg)

        if (gamemode == null) {
            player.localeMessage("command.gamemode.failure").with("gamemode", arg)
            return
        }

        player.gamemode = gamemode

        player.localeMessage("command.gamemode.success") {
            with("gamemode", gamemode.name.toLowerCase())
        }
    }
}

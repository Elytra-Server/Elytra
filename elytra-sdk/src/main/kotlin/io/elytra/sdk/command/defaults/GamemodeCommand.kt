package io.elytra.sdk.command.defaults

import io.elytra.api.command.CommandIssuer
import io.elytra.api.command.ElytraCommand
import io.elytra.api.command.annotations.CommandArgument
import io.elytra.api.command.annotations.CommandSpec
import io.elytra.api.command.argument.ArgumentList
import io.elytra.api.command.argument.ArgumentTypes
import io.elytra.api.entity.player.Player
import io.elytra.api.world.enums.GameMode
import io.elytra.sdk.utils.localeMessage

@CommandSpec(label = "gamemode", aliases = ["gm"])
class GamemodeCommand : ElytraCommand() {

    @CommandArgument("type", ArgumentTypes.Default::class)
    override fun execute(issuer: CommandIssuer, arguments: ArgumentList) {
        val player = issuer as Player
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

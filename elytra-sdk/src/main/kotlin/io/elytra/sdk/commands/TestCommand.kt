package io.elytra.sdk.commands

import io.elytra.api.command.CommandSender
import io.elytra.api.command.ElytraCommand
import io.elytra.api.command.annotations.CommandArgument
import io.elytra.api.command.annotations.CommandSpec
import io.elytra.api.command.argument.ArgumentList
import io.elytra.api.command.argument.ArgumentTypes
import io.elytra.api.entity.Player

@CommandSpec("test")
class TestCommand : ElytraCommand() {

    @CommandArgument("testArgument", ArgumentTypes.Default::class)
    override fun execute(sender: CommandSender, arguments: ArgumentList) {
        val player = sender as Player
        val argument = arguments.getValue<String>("testArgument")
        player.sendMessage("Your argument is \"$argument\"")
    }
}

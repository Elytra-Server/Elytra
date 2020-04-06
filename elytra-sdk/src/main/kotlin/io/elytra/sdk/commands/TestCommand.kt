package io.elytra.sdk.commands

import io.elytra.api.command.CommandInfo
import io.elytra.api.command.CommandSender
import io.elytra.api.command.ElytraCommand
import io.elytra.api.command.argument.ArgumentList
import io.elytra.api.command.argument.ArgumentTypes
import io.elytra.api.entity.Player

@CommandInfo("test")
class TestCommand : ElytraCommand() {

    init {
        addArgument("testArgument", ArgumentTypes.STRING)
    }

    override fun execute(sender: CommandSender, arguments: ArgumentList) {
        val player = sender as Player
        val argument = arguments.getValue<String>("testArgument")
        player.sendMessage("Your argument is \"$argument\"")
    }
}

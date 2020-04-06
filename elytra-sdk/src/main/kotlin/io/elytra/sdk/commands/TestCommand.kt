package io.elytra.sdk.commands

import io.elytra.api.command.BaseCommand
import io.elytra.api.command.CommandSender
import io.elytra.api.command.argument.ArgumentList
import io.elytra.api.command.argument.ArgumentTypes
import io.elytra.api.entity.Player

class TestCommand : BaseCommand("test") {

    init {
        addArgument("testArgument", ArgumentTypes.STRING)
    }

    override fun execute(sender: CommandSender, arguments: ArgumentList) {
        val player = sender as Player
        val argument = arguments.getValue<String>("testArgument")
        player.sendMessage("Your argument is \"$argument\"")
    }
}

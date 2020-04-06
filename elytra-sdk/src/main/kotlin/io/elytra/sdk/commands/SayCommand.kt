package io.elytra.sdk.commands

import io.elytra.api.command.CommandInfo
import io.elytra.api.command.CommandSender
import io.elytra.api.command.ElytraCommand
import io.elytra.api.command.argument.ArgumentList

@CommandInfo(label = "say")
class SayCommand : ElytraCommand() {

    override fun execute(sender: CommandSender, arguments: ArgumentList) {
    }
}

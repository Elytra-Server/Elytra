package io.elytra.sdk.command.defaults

import io.elytra.api.command.CommandIssuer
import io.elytra.api.command.ElytraCommand
import io.elytra.api.command.annotations.CommandArgument
import io.elytra.api.command.annotations.CommandSpec
import io.elytra.api.command.argument.ArgumentList
import io.elytra.api.command.argument.ArgumentTypes
import io.elytra.api.entity.Player

@CommandSpec("test", aliases = ["t"])
class TestCommand : ElytraCommand() {

    @CommandArgument("testArgument", ArgumentTypes.Default::class)
    override fun execute(issuer: CommandIssuer, arguments: ArgumentList) {
        val player = issuer as Player
        val argument = arguments.getValue<String>("testArgument")
        player.sendMessage("Your argument is \"$argument\"")
    }
}

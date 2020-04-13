package io.elytra.sdk.command.defaults

import io.elytra.api.command.CommandIssuer
import io.elytra.api.command.ElytraCommand
import io.elytra.api.command.annotations.CommandArgument
import io.elytra.api.command.annotations.CommandSpec
import io.elytra.api.command.argument.ArgumentList
import io.elytra.api.command.argument.ArgumentTypes
import io.elytra.sdk.server.Elytra

@CommandSpec(label = "say")
class SayCommand : ElytraCommand() {

    @CommandArgument("message", ArgumentTypes.Default::class)
    override fun execute(issuer: CommandIssuer, arguments: ArgumentList) {
        Elytra.server.broadcastMessage(arguments.getValue(0))
    }
}

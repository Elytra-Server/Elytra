package io.elytra.sdk.commands

import io.elytra.api.command.CommandIssuer
import io.elytra.api.command.ElytraCommand
import io.elytra.api.command.annotations.CommandSpec
import io.elytra.api.command.argument.ArgumentList

@CommandSpec(label = "say")
class SayCommand : ElytraCommand() {

    override fun execute(issuer: CommandIssuer, arguments: ArgumentList) {
    }
}

package io.elytra.api.command

import io.elytra.api.command.argument.Argument
import io.elytra.api.entity.Player

interface CommandExecutor {

	fun execute(player: Player, contextArguments: MutableList<Argument<*>>)

}

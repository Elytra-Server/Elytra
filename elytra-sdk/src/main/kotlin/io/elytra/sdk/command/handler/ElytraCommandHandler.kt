package io.elytra.sdk.command.handler

import io.elytra.api.command.Command
import io.elytra.api.command.argument.Argument
import io.elytra.api.command.handler.CommandHandler
import io.elytra.api.command.registry.CommandRegistry
import io.elytra.api.entity.Player
import io.elytra.sdk.utils.ElytraConsts

class ElytraCommandHandler(val commandRegistry: CommandRegistry) : CommandHandler {

	override fun handle(player: Player, string: String) {
		if (!string.startsWith(ElytraConsts.COMMAND_PREFIX)) return

		val stringWithoutPrefix = string.substring(1)
		val split = stringWithoutPrefix.split(" ")

		if (split.isEmpty()) return

		val commandName = split[0]

		val command: Command? = commandRegistry.getCommandByName(commandName)

		if (command == null) {
			player.sendMessage(ElytraConsts.COMMAND_NOT_FOUND_MESSAGE)
			return
		}

		val argumentList: MutableList<Argument<*>> = ArrayList()
		val stringArgumentList = split.subList(1, split.size)

		val requiredArgumentNumber = command.arguments.filter { it.required }.count()

		if (split.size < requiredArgumentNumber) {
			player.sendMessage("Wrong usage")
			TODO("Implement command usage message")
		}

		for ((index, argumentContext) in command.arguments.withIndex()) {
			val argument = argumentContext.type.parse(stringArgumentList, index)

			if (argument != null) {
				argumentList.add(argument as Argument<*>)
				continue
			}

			if (argumentContext.required) {
				player.sendMessage(
					ElytraConsts.COMMAND_COULDNT_PARSE_ARGUMENT.replace(
						"<argumentName>",
						argumentContext.name
					)
				)
			}

		}
		try {
			command.execute(player, argumentList)
		} catch (exception: Exception) {
			// TODO: Log command exception
			throw exception
		}

	}

}

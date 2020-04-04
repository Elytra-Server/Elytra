package io.elytra.sdk.command.handler

import io.elytra.api.command.Command
import io.elytra.api.command.argument.Argument
import io.elytra.api.command.handler.CommandHandler
import io.elytra.api.command.registry.CommandRegistry
import io.elytra.api.entity.Player
import io.elytra.sdk.command.argument.ArgumentListImpl
import io.elytra.sdk.utils.ElytraConsts

class ElytraCommandHandler(val commandRegistry: CommandRegistry) : CommandHandler {

	override fun handle(player: Player, message: String) {
		if (!message.startsWith(ElytraConsts.COMMAND_PREFIX)) return

		val messageWithoutPrefix = message.substring(1)
		val argumentStringArray = messageWithoutPrefix.split(" ")

		if (argumentStringArray.isEmpty()) return

		val commandName = argumentStringArray[0]

		val command: Command? = commandRegistry.getCommandByName(commandName)

		if (command == null) {
			player.sendMessage(ElytraConsts.COMMAND_NOT_FOUND_MESSAGE)
			return
		}

		val argumentList = ArgumentListImpl()
		val stringArgumentList = argumentStringArray.subList(1, argumentStringArray.size)

		val requiredArgumentNumber = command.getArguments().filter { it.required }.count()

		if (argumentStringArray.size < requiredArgumentNumber) {
			player.sendMessage("Wrong usage")
			TODO("Implement command usage message")
		}

		for ((index, argumentContext) in command.getArguments().withIndex()) {
			val argumentValue = argumentContext.type.parse(stringArgumentList, index)

			if (argumentValue != null) {
				argumentList += Argument(argumentValue, argumentContext)
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
			throw CommandException(message, exception)
		}

	}

	class CommandException(fullCommand: String, baseException: Exception) :
		Exception("An error occured while executing $fullCommand:" + baseException.localizedMessage)

}

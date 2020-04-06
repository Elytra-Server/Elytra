package io.elytra.sdk.command.handler

import io.elytra.api.command.Command
import io.elytra.api.command.CommandSender
import io.elytra.api.command.argument.Argument
import io.elytra.api.command.handler.CommandHandler
import io.elytra.api.command.registry.CommandRegistry
import io.elytra.sdk.command.argument.ArgumentListImpl
import io.elytra.sdk.utils.ElytraConsts
import io.elytra.sdk.utils.localeMessage

class ElytraCommandHandler(
    private val commandRegistry: CommandRegistry
) : CommandHandler {

    override fun handle(sender: CommandSender, message: String) {
        if (!message.startsWith(ElytraConsts.COMMAND_PREFIX)) return

        val messageWithoutPrefix = message.substring(1)

        if (messageWithoutPrefix.isEmpty()) {
            sender.localeMessage("command.not.found")
            return
        }

        val messageArray = messageWithoutPrefix.split(" ")

        val commandName = messageArray[0]

        val command: Command? = commandRegistry.getCommandByName(commandName)

        if (command == null) {
            sender.localeMessage("command.not.found")
            return
        }

        val argumentList = ArgumentListImpl()

        val requiredArgumentNumber = command.getArguments().filter { it.required }.count()

        val stringArgumentList = messageArray.subList(1, messageArray.size)

        if (stringArgumentList.size < requiredArgumentNumber) {
            sender.localeMessage("command.wrong.usage")
            return
        }

        for ((index, argumentContext) in command.getArguments().withIndex()) {
            val argumentValue = argumentContext.type.parse(stringArgumentList, index)

            if (argumentValue != null) {
                argumentList += Argument(argumentValue, argumentContext)
                continue
            }

            if (argumentContext.required) {
                sender.localeMessage("command.couldnt.parse.argument") {
                    with("argumentName", argumentContext.name)
                }
            }
        }
        try {
            command.execute(sender, argumentList)
        } catch (exception: Exception) {
            throw CommandException(message, exception)
        }
    }

    class CommandException(fullCommand: String, baseException: Exception) :
        Exception("An error occured while executing $fullCommand:" + baseException.localizedMessage)
}

package io.elytra.sdk.command

import io.elytra.api.command.Command
import io.elytra.api.command.CommandIssuer
import io.elytra.api.command.TabCompletion
import io.elytra.api.command.argument.ArgumentContainer
import io.elytra.api.command.argument.ArgumentType
import io.elytra.api.command.handler.CommandHandler
import io.elytra.api.command.registry.CommandRegistry
import io.elytra.sdk.utils.ElytraConsts
import io.elytra.sdk.utils.localeMessage
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class ElytraCommandHandler(
    private val commandRegistry: CommandRegistry
) : CommandHandler {

    private val argumentClassTypePool: ArgumentClassTypePool = ArgumentClassTypePool()

    override fun handleTabComplete(issuer: CommandIssuer, message: String, tabCompletion: TabCompletion) {
        val messageWithoutPrefix = message.substring(1)

        if (messageWithoutPrefix.isEmpty()) {
            issuer.localeMessage("command.not.found")
            return
        }

        val messageArray = messageWithoutPrefix.replaceMoreThanOneSpace().split(" ")

        val label = messageArray[0]

        var command: Command? = commandRegistry.getCommandByName(label)

        if (command == null) {
            command = commandRegistry.getCommandByAlias(label)
        }

        if (command == null) {
            return
        }

        command.onTabComplete(issuer, message, tabCompletion)
    }

    override fun handle(issuer: CommandIssuer, message: String) {
        if (!message.startsWith(ElytraConsts.COMMAND_PREFIX)) return

        val messageWithoutPrefix = message.substring(1)

        if (messageWithoutPrefix.isEmpty()) {
            issuer.localeMessage("command.not.found")
            return
        }

        val messageArray = messageWithoutPrefix.replaceMoreThanOneSpace().split(" ")

        val label = messageArray[0]

        var command: Command? = commandRegistry.getCommandByName(label)

        if (command == null) {
            command = commandRegistry.getCommandByAlias(label)
        }

        if (command == null) {
            issuer.localeMessage("command.not.found")
            return
        }

        val argumentList = ArgumentListImpl()

        val requiredArgumentNumber = command.argumentList.filter { it.required }.count()

        val stringArgumentList = messageArray.subList(1, messageArray.size)

        if (stringArgumentList.size < requiredArgumentNumber) {
            issuer.localeMessage("command.wrong.usage")
            return
        }

        for ((index, commandArgument) in command.argumentList.withIndex()) {
            val type = argumentClassTypePool.getOrCreateArgumentType(commandArgument.classType)
            val argumentValue = type.parse(stringArgumentList, index)

            if (argumentValue != null) {
                argumentList += ArgumentContainer(argumentValue, commandArgument)
                continue
            }

            if (commandArgument.required) {
                issuer.localeMessage("command.couldnt.parse.argument") {
                    with("argumentName", commandArgument.name)
                }
            }
        }

        try {
            command.execute(issuer, argumentList)
        } catch (exception: Exception) {
            throw CommandException(message, exception)
        }
    }

    class CommandException(fullCommand: String, baseException: Exception) :
        Exception("An error occured while executing $fullCommand:" + baseException.localizedMessage)

    class ArgumentClassTypePool {

        private val map: MutableMap<KClass<out ArgumentType<*>>, ArgumentType<*>> = HashMap()

        fun getOrCreateArgumentType(argumentTypeClass: KClass<out ArgumentType<*>>): ArgumentType<*> {
            val argumentType = map[argumentTypeClass]
            if (argumentType != null) {
                return argumentType
            }

            val argumentInstance = argumentTypeClass.createInstance()
            map[argumentTypeClass] = argumentInstance
            return argumentInstance
        }
    }

    private fun String.replaceMoreThanOneSpace(): String {
        return this.trim().replace(" +", " ")
    }
}

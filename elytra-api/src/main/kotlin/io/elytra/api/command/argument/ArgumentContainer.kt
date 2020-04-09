package io.elytra.api.command.argument

import io.elytra.api.command.annotations.CommandArgument

/***
 * Argument container acts like provider from the chat into the already parsed argument
 *
 * @param value value argument input
 * @param context command argument annotation used to define a spec for this container
 *
 * @sample /gamemode [value] - the [context] holds the info for what the [value] is (his type)
 */
class ArgumentContainer<T>(val value: T, val context: CommandArgument)

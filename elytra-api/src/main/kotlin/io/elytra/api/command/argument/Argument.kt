package io.elytra.api.command.argument

import io.elytra.api.command.annotations.CommandArgument

class Argument<T>(val value: T, val context: CommandArgument)

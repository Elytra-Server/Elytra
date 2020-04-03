package io.elytra.api.command.argument

interface ArgumentType<T> {

    fun parse(value: String): T?
}

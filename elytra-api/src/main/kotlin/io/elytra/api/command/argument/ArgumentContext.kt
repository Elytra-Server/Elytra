package io.elytra.api.command.argument

interface ArgumentContext<T> {

    val name: String

    val type: ArgumentType<T>

    val required: Boolean
}

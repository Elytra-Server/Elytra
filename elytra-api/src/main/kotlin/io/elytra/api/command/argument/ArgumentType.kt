package io.elytra.api.command.argument

/**
 * Represents an argument type
 *
 * @see [ArgumentTypes] already defined types
 */
interface ArgumentType<T> {

    /**
     * Parses the [inputArguments] to a certain type of [T]
     */
    fun parse(inputArguments: List<String>, index: Int): T?
}

package io.elytra.api.command.argument

/**
 * Provides a way to manage the arguments
 */
interface ArgumentList {

    /**
     * Returns the [ArgumentContainer] by his context name
     *
     * @return [ArgumentContainer] raw argument
     */
    fun <T> getArgument(argumentContextName: String): ArgumentContainer<T>?

    /**
     * Returns the [ArgumentContainer] by his index
     *
     * @return [ArgumentContainer] raw argument
     */
    fun <T> getArgument(argumentIndex: Int): ArgumentContainer<T>?

    /**
     * Returns the [ArgumentContainer] by his context name from an unrequired argument
     *
     * @return [ArgumentContainer] raw argument
     */
    fun <T> getUnrequiredArgument(argumentContextName: String): ArgumentContainer<T>?

    /**
     * Returns the [ArgumentContainer] by his index from an unrequired argument
     *
     * @return [ArgumentContainer] raw argument
     */
    fun <T> getUnrequiredArgument(argumentIndex: Int): ArgumentContainer<T>?

    /**
     * Returns the value from an argument by his context name
     *
     * @return the value from the argument
     */
    fun <T> getValue(argumentContextName: String): T

    /**
     * Returns the value from an argument by his index
     *
     * @return the value from the argument
     */
    fun <T> getValue(argumentIndex: Int): T

    /**
     * Returns the value from an unrequired argument by his context name
     *
     * @return the value from the argument
     */
    fun <T> getUnrequiredValue(argumentContextName: String): T?

    /**
     * Returns the value from an unrequired argument by his index
     *
     * @return the value from the argument
     */
    fun <T> getUnrequiredValue(argumentIndex: Int): T?

    /**
     * Returns all arguments within the command
     *
     * @return [List] of [ArgumentContainer]
     */
    fun getAll(): List<ArgumentContainer<*>>
}

package io.elytra.sdk.command

import io.elytra.api.command.argument.ArgumentContainer
import io.elytra.api.command.argument.ArgumentList

class ArgumentListImpl : ArgumentList {

    private val map: MutableMap<String, ArgumentContainer<Any>> = HashMap()
    private val list: MutableList<ArgumentContainer<Any>> = ArrayList()

    override fun <T> getArgument(argumentContextName: String): ArgumentContainer<T> = map[argumentContextName] as ArgumentContainer<T>

    override fun <T> getArgument(argumentIndex: Int): ArgumentContainer<T> = list[argumentIndex] as ArgumentContainer<T>

    override fun <T> getUnrequiredArgument(argumentContextName: String): ArgumentContainer<T>? = map[argumentContextName] as ArgumentContainer<T>?

    override fun <T> getUnrequiredArgument(argumentIndex: Int): ArgumentContainer<T>? = list[argumentIndex] as ArgumentContainer<T>?

    override fun <T> getValue(argumentContextName: String): T = getArgument<T>(argumentContextName).value

    override fun <T> getValue(argumentIndex: Int): T = getArgument<T>(argumentIndex).value

    override fun <T> getUnrequiredValue(argumentContextName: String): T? = getUnrequiredArgument<T>(argumentContextName)?.value

    override fun <T> getUnrequiredValue(argumentIndex: Int): T? = getUnrequiredArgument<T>(argumentIndex)?.value

    override fun getAll(): List<ArgumentContainer<*>> = list

    fun add(argumentContainer: ArgumentContainer<Any>) {
        map[argumentContainer.context.name] = argumentContainer
        list.add(argumentContainer)
    }

    operator fun plusAssign(argumentContainer: ArgumentContainer<Any>) {
        add(argumentContainer)
    }
}

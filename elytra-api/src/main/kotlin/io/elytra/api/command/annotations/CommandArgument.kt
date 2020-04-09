package io.elytra.api.command.annotations

import io.elytra.api.command.argument.ArgumentType
import kotlin.reflect.KClass

/***
 * Represents a command argument
 *
 * @param name argument name that will be obtained when parsing
 * @param classType type of argument
 * @param required require ability of the argument
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
annotation class CommandArgument(
    val name: String,
    val classType: KClass<out ArgumentType<*>>,
    val required: Boolean = true
)

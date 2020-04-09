package io.elytra.api.command.annotations

import io.elytra.api.command.argument.ArgumentType
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
annotation class CommandArgument(
    val name: String,
    val classType: KClass<out ArgumentType<*>>,
    val required: Boolean = true
)

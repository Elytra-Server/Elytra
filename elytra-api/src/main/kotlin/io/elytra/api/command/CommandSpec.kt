package io.elytra.api.command

/**
 * Command meta information to define a command
 *
 * @param label the label after the command prefix, ex: /<label here>
 * @param usage the usage message of the command
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CommandSpec(
    val label: String,
    val usage: String = ""
)

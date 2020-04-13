package io.elytra.api.command.annotations

/**
 * Command meta information to define a command
 *
 * @param label the label after the command prefix, ex: /<label here>
 * @param aliases all the aliases that the command will have
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CommandSpec(
    val label: String,
    val aliases: Array<String> = []
)

package io.elytra.api.plugin

/**
 * Represents a elytra plugin
 *
 * @param [name] plugin name
 * @param [version] plugin version
 * @param [description] plugin description
 * @param [authors] plugin authors
 * @param [dependencies] plugin dependencies
 *
 * @see [Dependency]
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Plugin(
    val name: String = "",
    val version: String = "",
    val description: String = "",
    val authors: Array<String> = [""],
    val dependencies: Array<Dependency> = []
)

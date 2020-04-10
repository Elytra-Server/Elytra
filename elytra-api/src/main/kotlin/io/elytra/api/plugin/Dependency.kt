package io.elytra.api.plugin

/**
 * Represents a dependency of a plugin, this meaning that you can use
 * other plugin as an api.
 *
 * @param [id] id of the dependency (usually its name)
 * @param [version] current dependency version
 * @param [optional] will not be required
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class Dependency(
    val id: String = "",
    val version: String = "",
    val optional: Boolean = false
)

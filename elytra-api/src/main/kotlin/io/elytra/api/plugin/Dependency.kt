package io.elytra.api.plugin

@Retention(AnnotationRetention.RUNTIME)
@Target()
annotation class Dependency(
    val id: String = "",
    val version: String = "",
    val optional: Boolean = false
)

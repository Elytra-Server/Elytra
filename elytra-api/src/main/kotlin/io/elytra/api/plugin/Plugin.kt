package io.elytra.api.plugin

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Plugin(
	val id: String = "",
	val name: String = "",
	val version: String = "",
	val description: String = "",
	val authors: Array<String> = [""],
	val dependencies: Array<Dependency> = []
)

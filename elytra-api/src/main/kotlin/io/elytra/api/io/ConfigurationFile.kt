package io.elytra.api.io


interface ConfigurationFile {

	fun load()

	fun reload()

	fun save()

	fun delete()

}

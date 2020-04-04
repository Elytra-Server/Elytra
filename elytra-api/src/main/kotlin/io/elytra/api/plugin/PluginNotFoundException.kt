package io.elytra.api.plugin

class PluginNotFoundException(private val plugin: Plugin) : Exception("Plugin ${plugin.name} wasn't found")

package io.elytra.api.plugin

/**
 * Thrown when theres no plugin found with a certain name
 *
 * @see [Plugin]
 */
class PluginNotFoundException(plugin: Plugin) : Exception("Plugin ${plugin.name} wasn't found")

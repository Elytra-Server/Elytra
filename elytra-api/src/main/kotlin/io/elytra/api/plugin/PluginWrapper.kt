package io.elytra.api.plugin

import java.nio.file.Path

/**
 * A wrapper around a class annotated with [Plugin] to manage
 * the information from the annotation.
*/
interface PluginWrapper {

	/**
	 * Returns the created instance of [Plugin] if it is available.
	 *
	 * @return The instance if available
	 */
	fun <T : Plugin> getInstance() : T

	/**
	 * Returns the absolute path the plugin was loaded from.
	 *
	 * @return The source the plugin was loaded from
	 * @throws [PluginNotFoundException] if is unknown
	 */
	@Throws(PluginNotFoundException::class)
	fun getAbsolutePath() : Path

}

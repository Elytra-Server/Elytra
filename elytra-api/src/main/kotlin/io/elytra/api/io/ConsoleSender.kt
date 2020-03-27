package io.elytra.api.io

/**
 * Uses the elytra logger to dispatch messages to the console
 */
interface ConsoleSender {

	fun info(message: String)

	fun warn(message: String)

	fun error(message: String)

	fun debug(message: String)

}

package io.elytra.api.io

/**
 * Providers basic logging operations
 */
interface ConsoleSender {

    fun info(message: String)

    fun warn(message: String)

    fun error(message: String)

    fun debug(message: String)
}

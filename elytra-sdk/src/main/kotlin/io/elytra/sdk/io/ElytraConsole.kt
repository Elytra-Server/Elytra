package io.elytra.sdk.io

import io.elytra.api.io.ConsoleSender
import io.elytra.api.io.LogLevel
import io.elytra.api.io.LogLevel.*
import io.elytra.api.utils.formatting.AnsiColors
import org.slf4j.Logger

class ElytraConsole(private val logger: Logger) : ConsoleSender {

    override fun info(message: String) {
        send(INFO, message)
    }

    override fun warn(message: String) {
        send(WARN, message)
    }

    override fun error(message: String) {
        send(ERROR, message)
    }

    override fun debug(message: String) {
        // Send raw message for debugging
        logger.debug(message)
    }

    private fun send(logLevel: LogLevel, msg: String) {
        val replacedMsg = AnsiColors.replaceColors("$msg&r")

        when (logLevel) {
            INFO -> logger.info(replacedMsg)
            ERROR -> logger.error(replacedMsg)
            WARN -> logger.warn(replacedMsg)
            DEBUG -> logger.debug(replacedMsg)
        }
    }
}

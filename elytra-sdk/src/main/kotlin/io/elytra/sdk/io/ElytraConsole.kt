package io.elytra.sdk.io

import io.elytra.api.io.ConsoleSender
import io.elytra.api.utils.formatting.AnsiColors
import org.slf4j.Logger

// TODO: refactor post-poned
class ElytraConsole(private val logger: Logger) : ConsoleSender {

    override fun info(message: String) {
        send(message) {
            logger.info(it)
        }
    }

    override fun warn(message: String) {
        send(message) {
            logger.warn(it)
        }
    }

    override fun error(message: String) {
        send(message) {
            logger.error(it)
        }
    }

    override fun debug(message: String) {
        send(message) {
            logger.debug(it)
        }
    }

    private fun send(
        msg: String,
        execute: (msg: String) -> Unit = {}
    ) {
        val message = AnsiColors.replaceByColor("$msg&r")
        execute.invoke(message)
    }
}

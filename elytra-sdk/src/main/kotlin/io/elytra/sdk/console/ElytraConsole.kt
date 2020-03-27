package io.elytra.sdk.console

import io.elytra.api.utils.formatting.AnsiColors
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ElytraConsole {

	private val logger: Logger = LoggerFactory.getLogger("Elytra")

	fun send(vararg text: StringBuilder) {
		text.forEach {
			val message = AnsiColors.replaceByColor(it.append("\u001B[0m").toString())
			logger.info(message)
		}
	}
}

package io.inb.api.console

import io.inb.api.server.InbServer
import io.inb.api.utils.formatting.AnsiColors

object ElytraConsole {

	fun send(text: StringBuilder) {
//		val message = AnsiColors.replaceByColor(text.append("\u001B[0m").toString())
		InbServer.logger.info(text.toString())
	}
}

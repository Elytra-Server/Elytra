package io.inb.sdk

import io.inb.api.protocol.CodecHandler
import io.inb.sdk.network.server.Server

fun main() {
	CodecHandler.load()

	Server().start()
}

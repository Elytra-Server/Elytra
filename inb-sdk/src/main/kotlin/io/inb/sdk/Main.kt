package io.inb.sdk

import io.inb.api.protocol.CodecHandler
import io.inb.api.protocol.PacketHandlers
import io.inb.sdk.network.server.Server

fun main() {
	Server().start()
}

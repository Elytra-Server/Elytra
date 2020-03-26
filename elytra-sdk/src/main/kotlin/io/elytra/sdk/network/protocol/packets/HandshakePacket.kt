package io.elytra.sdk.network.protocol.packets

import io.elytra.sdk.network.protocol.codecs.HandshakeCodec
import io.elytra.sdk.network.protocol.handlers.HandshakeHandler
import io.elytra.sdk.network.protocol.message.HandshakeMessage

class HandshakePacket : BasicPacket("HANDSHAKE", 0) {

	init {
		inbound(0x00,
			HandshakeMessage::class.java,
			HandshakeCodec::class.java,
			HandshakeHandler::class.java
		)
	}
}

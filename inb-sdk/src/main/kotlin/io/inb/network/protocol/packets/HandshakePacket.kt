package io.inb.network.protocol.packets

import io.inb.network.protocol.codecs.HandshakeCodec
import io.inb.network.protocol.handlers.HandshakeHandler
import io.inb.network.protocol.message.HandshakeMessage

class HandshakePacket : BasicPacket("HANDSHAKE", 0) {

	init {
		inbound(0x00,
			HandshakeMessage::class.java,
			HandshakeCodec::class.java,
			HandshakeHandler::class.java
		)
	}
}

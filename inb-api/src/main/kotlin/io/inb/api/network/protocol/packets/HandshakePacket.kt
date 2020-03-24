package io.inb.api.network.protocol.packets

import io.inb.api.network.protocol.codecs.HandshakeCodec
import io.inb.api.network.protocol.handlers.HandshakeHandler
import io.inb.api.network.protocol.message.HandshakeMessage

class HandshakePacket : BasicPacket("HANDSHAKE", 0) {

	init {
		inbound(
			0x00,
			HandshakeMessage::class.java,
			HandshakeCodec::class.java,
			HandshakeHandler()
		)
	}
}

package io.inb.api.network.protocol.packets

import io.inb.api.network.protocol.codecs.DisconnectCodec
import io.inb.api.network.protocol.message.DisconnectMessage

class LoginPacket : BasicPacket("LOGIN", 5) {

	init {
		outbound(0x00, DisconnectMessage::class.java, DisconnectCodec::class.java)
	}
}

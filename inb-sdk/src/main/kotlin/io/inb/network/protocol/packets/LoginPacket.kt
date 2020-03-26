package io.inb.network.protocol.packets

import io.inb.network.protocol.codecs.DisconnectCodec
import io.inb.network.protocol.codecs.login.LoginStartCodec
import io.inb.network.protocol.codecs.login.LoginSuccessCodec
import io.inb.network.protocol.handlers.login.*
import io.inb.network.protocol.message.DisconnectMessage
import io.inb.network.protocol.message.LoginStartMessage
import io.inb.network.protocol.message.login.LoginSuccessMessage

class LoginPacket : BasicPacket("LOGIN", 5) {

	init {
		inbound(
			0x00,
			LoginStartMessage::class.java,
			LoginStartCodec::class.java,
			LoginStartHandler::class.java
		)

		outbound(0x00, DisconnectMessage::class.java, DisconnectCodec::class.java)
		outbound(0x02, LoginSuccessMessage::class.java, LoginSuccessCodec::class.java)
	}
}

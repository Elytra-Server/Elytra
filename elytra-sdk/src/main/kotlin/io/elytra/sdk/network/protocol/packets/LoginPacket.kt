package io.elytra.sdk.network.protocol.packets

import io.elytra.sdk.network.protocol.codecs.DisconnectCodec
import io.elytra.sdk.network.protocol.codecs.login.LoginStartCodec
import io.elytra.sdk.network.protocol.codecs.login.LoginSuccessCodec
import io.elytra.sdk.network.protocol.handlers.login.LoginStartHandler
import io.elytra.sdk.network.protocol.message.DisconnectMessage
import io.elytra.sdk.network.protocol.message.LoginStartMessage
import io.elytra.sdk.network.protocol.message.login.LoginSuccessMessage

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

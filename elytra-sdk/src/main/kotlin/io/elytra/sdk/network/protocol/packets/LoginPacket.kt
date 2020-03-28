package io.elytra.sdk.network.protocol.packets

import io.elytra.sdk.network.protocol.ProtocolInfo
import io.elytra.sdk.network.protocol.codecs.DisconnectCodec
import io.elytra.sdk.network.protocol.codecs.login.LoginStartCodec
import io.elytra.sdk.network.protocol.codecs.login.LoginSuccessCodec
import io.elytra.sdk.network.protocol.handlers.login.LoginStartHandler
import io.elytra.sdk.network.protocol.message.DisconnectMessage
import io.elytra.sdk.network.protocol.message.login.LoginStartMessage
import io.elytra.sdk.network.protocol.message.login.LoginSuccessMessage

class LoginPacket : BasicPacket("LOGIN", 5) {

	init {
		inbound(
			ProtocolInfo.LOGIN_START,
			LoginStartMessage::class.java,
			LoginStartCodec::class.java,
			LoginStartHandler::class.java
		)

		outbound(ProtocolInfo.DISCONNECT, DisconnectMessage::class.java, DisconnectCodec::class.java)
		outbound(ProtocolInfo.LOGIN_SUCCESS, LoginSuccessMessage::class.java, LoginSuccessCodec::class.java)
	}
}

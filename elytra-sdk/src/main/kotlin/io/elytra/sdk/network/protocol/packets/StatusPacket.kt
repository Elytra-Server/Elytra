package io.elytra.sdk.network.protocol.packets

import io.elytra.sdk.network.protocol.codecs.status.PingCodec
import io.elytra.sdk.network.protocol.codecs.status.ServerQueryCodec
import io.elytra.sdk.network.protocol.codecs.status.ServerInfoCodec
import io.elytra.sdk.network.protocol.handlers.status.PingHandler
import io.elytra.sdk.network.protocol.handlers.status.ServerQueryHandler
import io.elytra.sdk.network.protocol.message.status.PingMessage
import io.elytra.sdk.network.protocol.message.status.ServerQueryMessage
import io.elytra.sdk.network.protocol.message.status.ServerInfoMessage

class StatusPacket : BasicPacket("STATUS", 2) {

	init {
		inbound(0x00, ServerQueryMessage::class.java, ServerQueryCodec::class.java, ServerQueryHandler::class.java)
		inbound(0x01, PingMessage::class.java, PingCodec::class.java, PingHandler::class.java)

		outbound(0x00, ServerInfoMessage::class.java, ServerInfoCodec::class.java)
		outbound(0x01, PingMessage::class.java, PingCodec::class.java)
	}
}

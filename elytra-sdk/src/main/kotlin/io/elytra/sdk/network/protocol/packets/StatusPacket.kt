package io.elytra.sdk.network.protocol.packets

import io.elytra.sdk.network.protocol.ProtocolInfo
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
		inbound(ProtocolInfo.SERVER_QUERY, ServerQueryMessage::class.java, ServerQueryCodec::class.java, ServerQueryHandler::class.java)
		inbound(ProtocolInfo.I_PING, PingMessage::class.java, PingCodec::class.java, PingHandler::class.java)

		outbound(ProtocolInfo.SERVER_INFO, ServerInfoMessage::class.java, ServerInfoCodec::class.java)
		outbound(ProtocolInfo.O_PING, PingMessage::class.java, PingCodec::class.java)
	}
}

package io.inb.network.protocol.packets

import io.inb.network.protocol.codecs.status.PingCodec
import io.inb.network.protocol.codecs.status.ServerQueryCodec
import io.inb.network.protocol.codecs.status.ServerInfoCodec
import io.inb.network.protocol.handlers.status.PingHandler
import io.inb.network.protocol.handlers.status.ServerQueryHandler
import io.inb.network.protocol.message.status.PingMessage
import io.inb.network.protocol.message.status.ServerQueryMessage
import io.inb.network.protocol.message.status.ServerInfoMessage

class StatusPacket : BasicPacket("STATUS", 2) {

	init {
		inbound(0x00, ServerQueryMessage::class.java, ServerQueryCodec::class.java, ServerQueryHandler::class.java)
		inbound(0x01, PingMessage::class.java, PingCodec::class.java, PingHandler::class.java)

		outbound(0x00, ServerInfoMessage::class.java, ServerInfoCodec::class.java)
		outbound(0x01, PingMessage::class.java, PingCodec::class.java)
	}
}

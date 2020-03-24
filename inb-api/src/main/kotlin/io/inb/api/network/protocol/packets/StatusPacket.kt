package io.inb.api.network.protocol.packets

import io.inb.api.network.protocol.codecs.status.StatusPingCodec
import io.inb.api.network.protocol.codecs.status.StatusRequestCodec
import io.inb.api.network.protocol.codecs.status.StatusResponseCodec
import io.inb.api.network.protocol.handlers.status.StatusPingHandler
import io.inb.api.network.protocol.handlers.status.StatusRequestHandler
import io.inb.api.network.protocol.message.status.StatusPingMessage
import io.inb.api.network.protocol.message.status.StatusRequestMessage
import io.inb.api.network.protocol.message.status.StatusResponseMessage

class StatusPacket : BasicPacket("STATUS", 2) {

	init {
		inbound(0x00, StatusRequestMessage::class.java, StatusRequestCodec::class.java,
			StatusRequestHandler::class.java)
		inbound(0x01, StatusPingMessage::class.java, StatusPingCodec::class.java, StatusPingHandler::class.java)
		outbound(0x00, StatusResponseMessage::class.java, StatusResponseCodec::class.java)
		outbound(0x01, StatusPingMessage::class.java, StatusPingCodec::class.java)
	}
}

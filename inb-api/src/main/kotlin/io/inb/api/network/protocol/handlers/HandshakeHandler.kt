package io.inb.api.network.protocol.handlers

import com.flowpowered.network.MessageHandler
import io.inb.api.network.InbSession
import io.inb.api.network.protocol.message.HandshakeMessage

class HandshakeHandler : MessageHandler<InbSession, HandshakeMessage> {

	override fun handle(inbSession: InbSession, message: HandshakeMessage) {
		println("Handshake [${message.address}:${message.port}] - ${message.version} (${message.state})")
	}

}

package io.inb.api.network.protocol.handlers

import com.flowpowered.network.MessageHandler
import io.inb.api.network.Session
import io.inb.api.network.protocol.message.HandshakeMessage
import io.inb.api.network.protocol.packets.BasicPacket

class HandshakeHandler : MessageHandler<Session, HandshakeMessage> {

	override fun handle(session: Session, message: HandshakeMessage) {
		println("Something")
	}

}

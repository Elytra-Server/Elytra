package io.inb.api.protocol.handlers

import io.inb.api.network.Session
import io.inb.api.network.State
import io.inb.api.protocol.PacketHandler
import io.inb.api.protocol.packets.HandshakePacket

class HandshakeHandler : PacketHandler<HandshakePacket> {

	override fun handle(session: Session, body: HandshakePacket) {
		if(session.state != State.HANDSHAKE){
			session.disconnect("Could not handshake!")
		}

		session.state = State.LOGIN
		session.sendPacket(HandshakePacket("-"))
	}

}

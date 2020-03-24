package io.inb.api.network.protocol.handlers

import com.flowpowered.network.MessageHandler
import io.inb.api.InbServer
import io.inb.api.network.InbSession
import io.inb.api.network.protocol.message.HandshakeMessage
import io.inb.api.network.protocol.packets.BasicPacket
import io.inb.api.network.protocol.packets.LoginPacket
import io.inb.api.network.protocol.packets.StatusPacket

class HandshakeHandler : MessageHandler<InbSession, HandshakeMessage> {

	override fun handle(inbSession: InbSession, message: HandshakeMessage) {
		val loginPacket = LoginPacket()

		val packet: BasicPacket

		if(message.state == 1){
			packet = StatusPacket()
		}else if(message.state == 2){
			packet = loginPacket
		}else {
			inbSession.disconnect("Invalid State")
			return
		}

		inbSession.setProtocol(packet)

		println(message.version)

		if(packet == loginPacket){
			if(message.version < InbServer.PROTOCOL_VERSION){
				inbSession.disconnect("Outdated client! Running: ${InbServer.GAME_VERSION}")
			}else if(message.version > InbServer.PROTOCOL_VERSION){
				inbSession.disconnect("Outdated server! Running: ${InbServer.GAME_VERSION}")
			}
		}

		println("Handshake [${message.address}:${message.port}] - ${message.version} (${message.state}) - ${inbSession.state}")
	}

}

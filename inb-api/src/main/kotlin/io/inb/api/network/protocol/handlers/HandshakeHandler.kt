package io.inb.api.network.protocol.handlers

import com.flowpowered.network.MessageHandler
import io.inb.api.InbServer
import io.inb.api.events.PlayerKickEvent
import io.inb.api.io.EventBus
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
			var reason = ""

			//TODO: Refactor to remove duplicated code
			if(message.version < InbServer.PROTOCOL_VERSION){
				reason = "Outdated client! Running: ${InbServer.GAME_VERSION}"
				inbSession.disconnect(reason)
				inbSession.player?.let { PlayerKickEvent(it, reason) }?.let { EventBus.post(it) }
			}else if(message.version > InbServer.PROTOCOL_VERSION){
				reason = "Outdated server! Running: ${InbServer.GAME_VERSION}"
				inbSession.disconnect(reason)

				inbSession.player?.let { PlayerKickEvent(it, reason) }?.let { EventBus.post(it) }
			}

		}

		println("Handshake [${message.address}:${message.port}] - ${message.version} (${message.state}) - ${inbSession.state}")
	}

}

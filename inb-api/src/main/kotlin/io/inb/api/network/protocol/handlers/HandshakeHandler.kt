package io.inb.api.network.protocol.handlers

import io.inb.api.server.Server
import io.inb.api.events.PlayerDisconnectEvent
import io.inb.api.io.EventBus
import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.message.HandshakeMessage
import io.inb.api.network.protocol.packets.BasicPacket
import io.inb.api.network.protocol.packets.LoginPacket
import io.inb.api.network.protocol.packets.StatusPacket
import io.inb.api.server.InbServer

class HandshakeHandler : InbMessageHandler<HandshakeMessage>() {

	override fun handle(networkSession: NetworkSession, message: HandshakeMessage) {
		val loginPacket = LoginPacket()

		val packet: BasicPacket

		if (message.state == 1) {
			packet = StatusPacket()
		} else if (message.state == 2) {
			packet = loginPacket
		} else {
			networkSession.disconnect("Invalid State")
			return
		}

		networkSession.setProtocol(packet)

		if (packet == loginPacket) {
			var reason = ""

			//TODO: Refactor to remove duplicated code
			if (message.version < InbServer.PROTOCOL_VERSION) {
				reason = "Outdated client! Running: ${InbServer.GAME_VERSION}"
				networkSession.disconnect(reason)

				networkSession.player?.let { PlayerDisconnectEvent(it, reason) }?.let { EventBus.post(it) }
			} else if (message.version > InbServer.PROTOCOL_VERSION) {
				reason = "Outdated server! Running: ${InbServer.GAME_VERSION}"
				networkSession.disconnect(reason)

				networkSession.player?.let { PlayerDisconnectEvent(it, reason) }?.let { EventBus.post(it) }
			}
		}

		println("Handshake [${message.address}:${message.port}] - ${message.version} (${message.state})")
	}

}

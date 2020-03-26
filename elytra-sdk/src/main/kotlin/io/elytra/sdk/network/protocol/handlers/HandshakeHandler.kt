package io.elytra.sdk.network.protocol.handlers

import io.elytra.api.events.PlayerDisconnectEvent
import io.elytra.api.io.EventBus
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.message.HandshakeMessage
import io.elytra.sdk.network.protocol.packets.BasicPacket
import io.elytra.sdk.network.protocol.packets.LoginPacket
import io.elytra.sdk.network.protocol.packets.StatusPacket
import io.elytra.api.server.InbServer
import io.elytra.sdk.network.protocol.ProtocolInfo

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
			val player = networkSession.player ?: return

			//TODO: Refactor to remove duplicated code
			if (message.version < ProtocolInfo.CURRENT_PROTOCOL) {
				reason = "Outdated client! Running: ${ProtocolInfo.MINECRAFT_VERSION}"
			} else if (message.version > ProtocolInfo.CURRENT_PROTOCOL) {
				reason = "Outdated server! Running: ${ProtocolInfo.MINECRAFT_VERSION}"
			}

			EventBus.post(PlayerDisconnectEvent(player, reason))
			networkSession.disconnect(reason)
		}

		InbServer.logger.debug("Handshake [${message.address}:${message.port}] - ${message.version} (${message.state})")
	}

}

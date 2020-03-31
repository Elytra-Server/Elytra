package io.elytra.sdk.network.protocol.handlers

import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.ProtocolInfo
import io.elytra.sdk.network.protocol.message.HandshakeMessage
import io.elytra.sdk.network.protocol.packets.BasicPacket
import io.elytra.sdk.network.protocol.packets.LoginPacket
import io.elytra.sdk.network.protocol.packets.Protocol
import io.elytra.sdk.network.protocol.packets.StatusPacket
import io.elytra.sdk.server.Elytra

class HandshakeHandler : ElytraMessageHandler<HandshakeMessage>() {

	override fun handle(networkSession: NetworkSession, message: HandshakeMessage) {

		val protocol: Protocol = when(message.state){
			1 -> Protocol.STATUS
			2 -> Protocol.LOGIN
			else -> {
				networkSession.disconnect("Invalid State")
				return
			}
		}

		networkSession.protocol(protocol)

		if (protocol == Protocol.LOGIN) {
			if (message.version < ProtocolInfo.CURRENT_PROTOCOL || message.version > ProtocolInfo.CURRENT_PROTOCOL) {
				val reason = if (message.version < ProtocolInfo.CURRENT_PROTOCOL) "Outdated client! Running: ${ProtocolInfo.MINECRAFT_VERSION}"
				else "Outdated server! Running: ${ProtocolInfo.MINECRAFT_VERSION}"
				networkSession.disconnect(reason)
			}
		}

		Elytra.console.debug("Handshake [${message.address}:${message.port}] - ${message.version} (${message.state})")
	}

}

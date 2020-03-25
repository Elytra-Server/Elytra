package io.inb.api.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.message.play.ClientSettingsMessage
import io.inb.api.network.protocol.message.status.StatusPingMessage

class ClientSettingsHandler : MessageHandler<NetworkSession, ClientSettingsMessage> {

	override fun handle(session: NetworkSession, message: ClientSettingsMessage) {
		println("Client Settings")
	}
}

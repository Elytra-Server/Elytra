package io.inb.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import io.inb.network.NetworkSession
import io.inb.network.protocol.message.play.ClientSettingsMessage
import io.inb.network.protocol.message.play.HeldItemChangeMessage

class ClientSettingsHandler : MessageHandler<NetworkSession, ClientSettingsMessage> {

	override fun handle(session: NetworkSession, message: ClientSettingsMessage) {
		println("Client Settings")

		session.send(HeldItemChangeMessage(0))

	}
}

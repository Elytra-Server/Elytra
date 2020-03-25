package io.inb.api.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.message.play.ClientSettingsMessage
import io.inb.api.network.protocol.message.play.ConfirmTeleportMessage

class ConfirmTeleportHandle : MessageHandler<NetworkSession, ConfirmTeleportMessage> {

	override fun handle(session: NetworkSession, message: ConfirmTeleportMessage) {
		println("Confirm Teleport")
	}
}

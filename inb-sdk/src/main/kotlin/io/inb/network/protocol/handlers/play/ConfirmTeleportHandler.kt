package io.inb.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import io.inb.network.NetworkSession
import io.inb.network.protocol.message.play.ConfirmTeleportMessage

class ConfirmTeleportHandler : MessageHandler<NetworkSession, ConfirmTeleportMessage> {

	override fun handle(session: NetworkSession, message: ConfirmTeleportMessage) {
		println("Confirm Teleport")
	}
}

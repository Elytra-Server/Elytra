package io.inb.api.network.protocol.handlers.play

import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.handlers.InbMessageHandler
import io.inb.api.network.protocol.message.play.PlayerPositionMessage

class PlayerPositionHandler : InbMessageHandler<PlayerPositionMessage>() {
	override fun handle(session: NetworkSession?, message: PlayerPositionMessage?) {

	}
}

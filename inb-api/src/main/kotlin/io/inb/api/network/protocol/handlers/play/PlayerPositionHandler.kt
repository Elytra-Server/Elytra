package io.inb.api.network.protocol.handlers.play

import io.inb.api.network.NetworkSession
import io.inb.api.network.State
import io.inb.api.network.protocol.handlers.InbMessageHandler
import io.inb.api.network.protocol.message.play.PlayerPositionMessage
import io.inb.api.world.Location

class PlayerPositionHandler : InbMessageHandler<PlayerPositionMessage>() {
	override fun handle(session: NetworkSession?, message: PlayerPositionMessage?) {
		if(session?.state != State.PLAYING) return

		if (message != null) {
			session.player?.location = Location(message.x, message.y, message.z, 0f, 0f)
		}
	}
}

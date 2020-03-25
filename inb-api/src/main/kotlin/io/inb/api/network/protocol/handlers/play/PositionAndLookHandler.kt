package io.inb.api.network.protocol.handlers.play

import io.inb.api.network.NetworkSession
import io.inb.api.network.State
import io.inb.api.network.protocol.handlers.InbMessageHandler
import io.inb.api.network.protocol.message.play.PositionAndLookMessage
import io.inb.api.world.Location

class PositionAndLookHandler : InbMessageHandler<PositionAndLookMessage>() {
	override fun handle(session: NetworkSession?, message: PositionAndLookMessage?) {
		if(session?.state != State.PLAYING) return

		if (message != null) {
			session.player?.location = Location(message.x, message.y, message.z, message.yaw, message.pitch)
		}
	}

}

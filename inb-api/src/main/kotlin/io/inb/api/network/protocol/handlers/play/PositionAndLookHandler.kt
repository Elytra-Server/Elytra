package io.inb.api.network.protocol.handlers.play

import io.inb.api.network.NetworkSession
import io.inb.api.network.State
import io.inb.api.network.protocol.handlers.InbMessageHandler
import io.inb.api.network.protocol.message.play.PlayerPositionAndLookMessage
import io.inb.api.world.Location

class PositionAndLookHandler : InbMessageHandler<PlayerPositionAndLookMessage>() {
	override fun handle(session: NetworkSession?, messagePlayer: PlayerPositionAndLookMessage?) {
		if(session?.state != State.PLAYING) return

		if (messagePlayer != null) {
			session.player?.location = Location(messagePlayer.x, messagePlayer.y, messagePlayer.z, messagePlayer.yaw, messagePlayer.pitch)
		}
	}

}

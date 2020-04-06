package io.elytra.sdk.network.protocol.handlers.play

import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.handlers.ElytraMessageHandler
import io.elytra.sdk.network.protocol.message.play.inbound.PlayerMovementMessage
import io.elytra.sdk.server.Elytra

class PlayerMovementHandler : ElytraMessageHandler<PlayerMovementMessage>() {

    override fun handle(session: NetworkSession?, message: PlayerMovementMessage) {
        val player = Elytra.server.playerRegistry.get(session!!)

        val oldPosition = player?.position
        val newPosition = oldPosition?.clone()

        message.update(newPosition!!)
    }
}

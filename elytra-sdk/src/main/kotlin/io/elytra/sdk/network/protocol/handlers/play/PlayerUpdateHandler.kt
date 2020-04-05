package io.elytra.sdk.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.message.play.PlayerPositionMessage
import io.elytra.sdk.server.Elytra

class PlayerUpdateHandler : MessageHandler<NetworkSession, PlayerPositionMessage> {

    override fun handle(session: NetworkSession?, message: PlayerPositionMessage) {
        val player = Elytra.server.playerRegistry.get(session?.sessionId!!)
        println("Andando ${message.x}")

        require(player != null) { "Player cannot be null" }

        val oldPosition = player.position
        val newPosition = oldPosition.clone()

        message.update(newPosition)
    }
}

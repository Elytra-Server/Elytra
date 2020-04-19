package io.elytra.sdk.network.protocol.handlers.play

import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.handlers.ElytraMessageHandler
import io.elytra.sdk.network.protocol.message.play.inbound.PlayerAbilitiesMessage

class PlayerAbilitiesHandler : ElytraMessageHandler<PlayerAbilitiesMessage>() {
    override fun handle(session: NetworkSession, message: PlayerAbilitiesMessage) {
        TODO("Not yet implemented")
    }
}

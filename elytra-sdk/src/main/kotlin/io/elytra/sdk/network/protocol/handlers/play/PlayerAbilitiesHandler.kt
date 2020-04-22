package io.elytra.sdk.network.protocol.handlers.play

import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.handlers.ElytraMessageHandler
import io.elytra.sdk.network.protocol.message.play.inbound.PlayerAbilitiesMessage
import io.elytra.sdk.server.Elytra

class PlayerAbilitiesHandler : ElytraMessageHandler<PlayerAbilitiesMessage>() {
    override fun handle(session: NetworkSession, message: PlayerAbilitiesMessage) {
        val player = Elytra.server.playerRegistry.get(session)!!
        val flyingFlag = message.flags and 0x02 != 0

        val canFly: Boolean = player.flying

        if (!flyingFlag || canFly) {
            player.flying = flyingFlag
        }
    }
}

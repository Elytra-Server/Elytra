package io.elytra.sdk.network.protocol.handlers

import com.flowpowered.network.Message
import com.flowpowered.network.MessageHandler
import io.elytra.api.io.i18n.I18n
import io.elytra.sdk.entity.ElytraPlayer
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.server.Elytra
import java.lang.IllegalArgumentException

abstract class ElytraMessageHandler<M : Message> : MessageHandler<NetworkSession, M> {
    fun getPlayerOrDisconnect(session: NetworkSession): ElytraPlayer {
        val player = Elytra.server.playerRegistry.get(session)
        if (player == null) {
            session.disconnect(I18n["player.invalidSession"])
            throw IllegalArgumentException("Tried to get player ${session.gameProfile?.name ?: "unknown"} when not available")
        }

        return player as ElytraPlayer
    }
}

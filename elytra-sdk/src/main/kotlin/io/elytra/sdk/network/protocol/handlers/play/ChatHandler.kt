package io.elytra.sdk.network.protocol.handlers.play

import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.handlers.ElytraMessageHandler
import io.elytra.sdk.network.protocol.message.play.inbound.ChatMessage
import io.elytra.sdk.server.Elytra
import io.elytra.sdk.utils.ElytraConsts

class ChatHandler : ElytraMessageHandler<ChatMessage>() {
    override fun handle(session: NetworkSession, message: ChatMessage) {
        val player = getPlayerOrDisconnect(session)

        if (message.content.startsWith(ElytraConsts.COMMAND_PREFIX)) {
            Elytra.server.commandHandler.handle(player, message.content)
            return
        }

        val content = message.content.replace('&', '\u00A7')
        Elytra.broadcast("chat.format") {
            with(
                "player" to session.gameProfile!!.name,
                "message" to content
            )
        }
    }
}

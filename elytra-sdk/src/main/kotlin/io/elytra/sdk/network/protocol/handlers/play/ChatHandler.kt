package io.elytra.sdk.network.protocol.handlers.play

import io.elytra.api.chat.ChatMode
import io.elytra.api.chat.TextComponent
import io.elytra.api.io.i18n.MessageBuilder
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.handlers.ElytraMessageHandler
import io.elytra.sdk.network.protocol.message.play.inbound.ChatMessage
import io.elytra.sdk.network.protocol.message.play.outbound.OutboundChatMessage
import io.elytra.sdk.server.Elytra
import io.elytra.sdk.utils.ElytraConsts

class ChatHandler : ElytraMessageHandler<ChatMessage>() {
    override fun handle(session: NetworkSession, message: ChatMessage) {
        val player = getPlayerOrDisconnect(session)

        if (message.content.startsWith(ElytraConsts.COMMAND_PREFIX)) {
            Elytra.server.commandHandler.handle(player, message.content)
            return
        }

        val chatMessage = MessageBuilder("chat.format")
            .with("player" to session.gameProfile!!.name)
            .with("message" to message.content)
            .getOrBuild()

        Elytra.sendPacketToAll(OutboundChatMessage(TextComponent(chatMessage), ChatMode.PLAYER))
    }
}

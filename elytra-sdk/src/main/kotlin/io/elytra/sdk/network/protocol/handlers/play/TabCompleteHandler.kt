package io.elytra.sdk.network.protocol.handlers.play

import io.elytra.api.chat.TextComponent
import io.elytra.api.command.TabCompletion
import io.elytra.api.command.handler.CommandHandler
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.handlers.ElytraMessageHandler
import io.elytra.sdk.network.protocol.message.play.outbound.TabCompleteMessage
import io.elytra.sdk.network.protocol.message.play.outbound.TabCompleteResponseMessage
import org.koin.core.KoinComponent
import org.koin.core.inject

class TabCompleteHandler : ElytraMessageHandler<TabCompleteMessage>(), KoinComponent {
    val commandHandler: CommandHandler by inject()

    override fun handle(session: NetworkSession, message: TabCompleteMessage) {
        val content = message.text

        if (content.isNotEmpty() && content[0] == '/') {
            val player = getPlayerOrDisconnect(session)

            val completions = commandHandler.handleTabComplete(player, content)
            val lastArgIndex = content.lastIndexOf(' ') + 1
            val lastArg = content.substring(lastArgIndex)

            session.send(TabCompleteResponseMessage(
                transactionId = message.transactionId,
                startIndex = lastArgIndex,
                textLength = lastArg.length,
                completions = completions.map { TabCompletion(it, TextComponent(it)) }
            ))
        }
    }
}

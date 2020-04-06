package io.elytra.sdk.network.protocol.handlers.play

import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.handlers.ElytraMessageHandler
import io.elytra.sdk.network.protocol.message.play.outbound.TabCompleteMessage

class TabCompleteHandler : ElytraMessageHandler<TabCompleteMessage>() {
    override fun handle(session: NetworkSession, message: TabCompleteMessage) {
        val content = message.text

        if (content.isNotEmpty() && content[0] == '/') {
            TODO("Execute tab complete and send response packet")
        }
    }
}

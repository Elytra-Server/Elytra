package io.elytra.sdk.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.message.play.KeepAliveMessage

class KeepAliveHandler : MessageHandler<NetworkSession, KeepAliveMessage> {

    override fun handle(session: NetworkSession, message: KeepAliveMessage) {
        session.ping(message.id)
    }
}

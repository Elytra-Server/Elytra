package io.elytra.sdk.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.message.play.inbound.ClientSettingsMessage

class ClientSettingsHandler : MessageHandler<NetworkSession, ClientSettingsMessage> {

    override fun handle(session: NetworkSession, message: ClientSettingsMessage) {
    }
}

package io.elytra.sdk.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.message.play.outbound.CustomPayloadMessage

class CustomPayloadHandler : MessageHandler<NetworkSession, CustomPayloadMessage> {

    override fun handle(session: NetworkSession, message: CustomPayloadMessage) {
    }
}

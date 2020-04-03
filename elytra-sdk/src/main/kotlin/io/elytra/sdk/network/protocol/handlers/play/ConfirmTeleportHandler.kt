package io.elytra.sdk.network.protocol.handlers.play

import com.flowpowered.network.MessageHandler
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.message.play.ConfirmTeleportMessage

class ConfirmTeleportHandler : MessageHandler<NetworkSession, ConfirmTeleportMessage> {

    override fun handle(session: NetworkSession, message: ConfirmTeleportMessage) {
    }
}

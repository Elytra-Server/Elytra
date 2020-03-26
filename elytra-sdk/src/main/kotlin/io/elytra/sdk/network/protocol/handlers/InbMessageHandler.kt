package io.elytra.sdk.network.protocol.handlers

import com.flowpowered.network.Message
import com.flowpowered.network.MessageHandler
import io.elytra.sdk.network.NetworkSession

abstract class InbMessageHandler<M : Message> : MessageHandler<NetworkSession, M>

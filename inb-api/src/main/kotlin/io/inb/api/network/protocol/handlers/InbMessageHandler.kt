package io.inb.api.network.protocol.handlers

import com.flowpowered.network.Message
import com.flowpowered.network.MessageHandler
import io.inb.api.network.NetworkSession

abstract class InbMessageHandler<M : Message> : MessageHandler<NetworkSession, M>

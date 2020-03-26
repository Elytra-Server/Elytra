package io.elytra.sdk.network.protocol.message.status

import com.flowpowered.network.Message
import io.elytra.api.utils.Asyncable

data class PingMessage(val time: Long) : Message, Asyncable

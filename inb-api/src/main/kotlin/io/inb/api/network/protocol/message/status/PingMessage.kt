package io.inb.api.network.protocol.message.status

import com.flowpowered.network.Message
import io.inb.api.utils.Asyncable

data class PingMessage(val time: Long) : Message, Asyncable

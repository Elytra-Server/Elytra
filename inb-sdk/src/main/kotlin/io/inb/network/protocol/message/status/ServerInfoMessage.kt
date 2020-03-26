package io.inb.network.protocol.message.status

import com.flowpowered.network.Message
import io.inb.api.utils.Asyncable

data class ServerInfoMessage(val body: String) : Message, Asyncable

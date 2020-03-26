package io.inb.network.protocol.message

import com.flowpowered.network.Message
import io.inb.api.utils.Asyncable

data class DisconnectMessage(val message: String) : Message, Asyncable

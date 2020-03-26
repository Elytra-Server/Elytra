package io.inb.network.protocol.message

import com.flowpowered.network.Message
import io.inb.api.utils.Asyncable

data class LoginStartMessage(val username: String) : Message, Asyncable

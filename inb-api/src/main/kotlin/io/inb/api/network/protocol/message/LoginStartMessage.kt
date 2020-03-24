package io.inb.api.network.protocol.message

import com.flowpowered.network.Message

data class LoginStartMessage(val username: String) : Message

package io.inb.network.protocol.message.login

import com.flowpowered.network.Message
import io.inb.api.utils.Asyncable

data class LoginSuccessMessage(val uuid: String, val username: String) : Message, Asyncable

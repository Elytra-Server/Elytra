package io.inb.api.network.protocol.message.login

import com.flowpowered.network.Message

data class LoginSuccessMessage(val uuid: String, val username: String) : Message

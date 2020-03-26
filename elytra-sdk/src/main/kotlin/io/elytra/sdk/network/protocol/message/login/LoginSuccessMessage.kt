package io.elytra.sdk.network.protocol.message.login

import com.flowpowered.network.Message
import io.elytra.api.utils.Asyncable

data class LoginSuccessMessage(val uuid: String, val username: String) : Message, Asyncable

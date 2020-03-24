package io.inb.api.network.protocol.message.status

import com.flowpowered.network.Message
import com.google.gson.JsonObject

data class StatusResponseMessage(val body: JsonObject) : Message

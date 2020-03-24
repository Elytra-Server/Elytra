package io.inb.api.network.protocol.message.status

import com.flowpowered.network.Message

data class StatusPingMessage(val time: Long) : Message

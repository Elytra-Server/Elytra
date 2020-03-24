package io.inb.api.network.protocol.message

import com.flowpowered.network.Message

data class HandshakeMessage(val version: Int,
							val address: String,
							val port: Int,
							val state: Int
) : Message

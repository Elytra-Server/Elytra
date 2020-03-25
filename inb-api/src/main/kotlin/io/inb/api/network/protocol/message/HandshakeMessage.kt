package io.inb.api.network.protocol.message

import com.flowpowered.network.Message
import io.inb.api.utils.Asyncable

data class HandshakeMessage(
	val version: Int,
	val address: String,
	val port: Int,
	val state: Int
) : Message, Asyncable

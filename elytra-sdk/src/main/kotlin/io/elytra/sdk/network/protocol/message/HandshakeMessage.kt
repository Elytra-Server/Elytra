package io.elytra.sdk.network.protocol.message

import com.flowpowered.network.Message
import io.elytra.api.utils.Asyncable

data class HandshakeMessage(
    val version: Int,
    val address: String,
    val port: Int,
    val state: Int
) : Message, Asyncable

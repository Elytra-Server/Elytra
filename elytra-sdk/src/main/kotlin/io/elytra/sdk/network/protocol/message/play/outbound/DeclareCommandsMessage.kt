package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message
import io.elytra.api.command.Command

data class DeclareCommandsMessage(
    val commands: Collection<Command>
) : Message

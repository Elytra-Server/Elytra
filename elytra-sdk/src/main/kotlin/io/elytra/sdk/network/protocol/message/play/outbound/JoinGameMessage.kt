package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message
import io.elytra.api.world.enums.GameMode

data class JoinGameMessage(
    val id: Int,
    val gameMode: GameMode,
    val dimension: Int,
    val seed: Long,
    val maxPlayers: Int,
    val worldType: String,
    val viewDistance: Int,
    val reducedDebugInfo: Boolean,
    val respawnScreen: Boolean
) : Message

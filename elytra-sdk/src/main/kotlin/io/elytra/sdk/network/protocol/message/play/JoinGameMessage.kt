package io.elytra.sdk.network.protocol.message.play

import com.flowpowered.network.Message
import io.elytra.api.world.Difficulty
import io.elytra.api.world.GameMode

data class JoinGameMessage(
        val id: Int,
        val gameMode: GameMode,
        val dimension: Int,
        val difficulty: Difficulty,
        val maxPlayers: Int,
        val worldType: String,
        val reducedDebugInfo: Boolean
) : Message

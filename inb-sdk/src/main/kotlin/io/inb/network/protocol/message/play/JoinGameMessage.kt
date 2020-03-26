package io.inb.network.protocol.message.play

import com.flowpowered.network.Message
import io.inb.api.world.Difficulty
import io.inb.api.world.GameMode

data class JoinGameMessage(
        val id: Int,
        val gameMode: GameMode,
        val dimension: Int,
        val difficulty: Difficulty,
        val maxPlayers: Int,
        val worldType: String,
        val reducedDebugInfo: Boolean
) : Message

package io.elytra.sdk.network.protocol.message.play

import com.flowpowered.network.Message
import io.elytra.api.world.enums.Difficulty
import io.elytra.api.world.enums.GameMode

data class JoinGameMessage(
	val id: Int,
	val gameMode: GameMode,
	val dimension: Int,
	val difficulty: Difficulty,
	val maxPlayers: Int,
	val worldType: String,
	val reducedDebugInfo: Boolean
) : Message

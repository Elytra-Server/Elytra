package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message

enum class GameMode(val value: Int) {
	SURVIVAL(0), CREATIVE(1), ADVENTURE(2), HARDCORE(3)
}

data class JoinGameMessage(
	val id: Int,
	val gameMode: Int,
	val dimension: Int,
	val difficulty: Int,
	val maxPlayers: Int,
	val worldType: String,
	val reducedDebugInfo: Boolean
) : Message

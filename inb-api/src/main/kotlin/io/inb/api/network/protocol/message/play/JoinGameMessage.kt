package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message

enum class GameMode(val value: Int) {
	SURVIVAL(0), CREATIVE(1), ADVENTURE(2), HARDCORE(3)
}

data class JoinGameMessage(
	val id: Int,
	val gameMode: Int,
	val maxPlayers: Int,
	val levelType: String,
	val viewDistance: Int,
	val reducedDebugInfo: Boolean,
	val enableRespawnScreen: Boolean
) : Message

package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message

data class JoinGameMessage(
	val id: Int,
	val gameMode: Int,
	val dimension: Int,
	val seed: Int,
	val maxPlayers: Int,
	val levelType: String,
	val viewDistance: Int,
	val reducedDebugInfo: Boolean,
	val enableRespawnScreen: Boolean
) : Message

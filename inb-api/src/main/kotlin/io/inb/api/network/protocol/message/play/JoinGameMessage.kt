package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message

data class JoinGameMessage(
	val id: Int,
	val gameMode: Int,
	val dimension: Int,
	val difficulty: Int,
	val maxPlayers: Int,
	val levelType: String
) : Message

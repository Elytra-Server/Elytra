package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message

enum class EnumDifficulty(val value: Int) {
	PEACEFUL(0), EASY(1), NORMAL(2), HARD(3)
}

data class ServerDifficultyMessage(val difficulty: EnumDifficulty) : Message

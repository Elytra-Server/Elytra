package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message
import io.inb.api.world.EnumDifficulty

data class ServerDifficultyMessage(val difficulty: EnumDifficulty) : Message

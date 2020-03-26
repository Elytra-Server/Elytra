package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message
import io.inb.api.world.Difficulty

data class ServerDifficultyMessage(val difficulty: Difficulty) : Message

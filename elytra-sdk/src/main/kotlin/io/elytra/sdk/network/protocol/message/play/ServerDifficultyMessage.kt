package io.elytra.sdk.network.protocol.message.play

import com.flowpowered.network.Message
import io.elytra.api.world.enums.Difficulty

data class ServerDifficultyMessage(val difficulty: Difficulty) : Message

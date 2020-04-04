package io.elytra.sdk.network.protocol.message.play

import com.flowpowered.network.Message
import io.elytra.api.chat.TextComponent

data class TitleMessage(
    val type: Type,
    val message: TextComponent?,
    val fadeInTime: Int = -1,
    val displayTime: Int = -1,
    val fadeOutTime: Int = -1
) : Message {

    enum class Type {
        TITLE, SUBTITLE, ACTIONBAR, TIMES, CLEAR, RESET
    }
}

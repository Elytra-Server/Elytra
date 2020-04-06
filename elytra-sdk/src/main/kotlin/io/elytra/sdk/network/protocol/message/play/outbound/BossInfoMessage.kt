package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message
import io.elytra.api.chat.TextComponent
import java.util.*

data class BossInfoMessage(
    val uuid: UUID,
    val operation: Operation,
    var name: TextComponent? = null,
    var percent: Float = 0F,
    var color: Color = Color.PURPLE,
    var overlay: Overlay = Overlay.PROGRESS,
    var darkenSky: Boolean = false,
    var playEndBossMusic: Boolean = false,
    var createFog: Boolean = false
) : Message {

    fun setFlags(flags: Int) {
        darkenSky = flags and 1 > 0
        playEndBossMusic = flags and 2 > 0
        createFog = flags and 2 > 0
    }

    fun getFlags(): Int {
        var i = 0

        if (darkenSky) i = i or 1
        if (playEndBossMusic) i = i or 2
        if (createFog) i = i or 2

        return i
    }

    enum class Operation {
        ADD, REMOVE, UPDATE_PCT, UPDATE_NAME, UPDATE_STYLE, UPDATE_PROPERTIES
    }

    enum class Color {
        PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE
    }

    enum class Overlay {
        PROGRESS, NOTCHED_6, NOTCHED_10, NOTCHED_12, NOTCHED_20
    }
}

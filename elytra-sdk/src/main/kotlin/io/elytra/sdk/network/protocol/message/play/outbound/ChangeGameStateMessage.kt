package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message

data class ChangeGameStateMessage(
    val type: Int = 0,
    val value: Float = 0f
) : Message {

    constructor(reason: Reason, value: Float) : this(reason.ordinal, value)

    enum class Reason {
        INVALID_BED, STOP_RAIN, START_RAIN, GAMEMODE, CREDITS, DEMO_MESSAGE, ARROW, RAIN_DENSITY, SKY_DARKNESS
    }
}

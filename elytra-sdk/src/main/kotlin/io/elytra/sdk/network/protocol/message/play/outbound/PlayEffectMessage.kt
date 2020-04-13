package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message
import io.elytra.api.world.Position

data class PlayEffectMessage(
    val id: Int = 0,
    val x: Int = 0,
    val y: Int = 0,
    val z: Int = 0,
    val data: Int = 0,
    val ignoreDistance: Boolean = false
) : Message {
    constructor(id: Int, position: Position, data: Int) :
        this(id, position.x.toInt(), position.y.toInt(), position.z.toInt(), data)
}

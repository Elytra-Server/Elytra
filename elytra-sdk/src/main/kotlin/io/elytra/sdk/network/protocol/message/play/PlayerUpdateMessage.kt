package io.elytra.sdk.network.protocol.message.play

import com.flowpowered.network.Message
import io.elytra.api.utils.Updatable
import io.elytra.api.world.Position

data class PlayerUpdateMessage(
    val onGround: Boolean
) : Message, Updatable<Position> {
    override fun update(record: Position) {}
}

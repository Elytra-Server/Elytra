package io.elytra.sdk.network.protocol.message.play.inbound

import com.flowpowered.network.Message
import io.elytra.api.utils.Updatable
import io.elytra.api.world.Position

open class PlayerMovementMessage(
    val onGround: Boolean
) : Message, Updatable<Position> {
    override fun update(record: Position) {}
}

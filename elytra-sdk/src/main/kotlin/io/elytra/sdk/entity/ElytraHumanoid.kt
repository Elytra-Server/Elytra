package io.elytra.sdk.entity

import io.elytra.api.entity.LivingEntity
import io.elytra.api.world.Position
import io.elytra.api.world.World

/**
 * Represents a human entity, such as an NPC or a player.
 */
open class ElytraHumanoid(
    health: Double = 20.0,
    override var world: World,
    override var position: Position
) : LivingEntity {

    override var health: Double = health
        set(value) {
            if (health < 0) field = 0.0
            if (health > 20.0) field = 20.0

            field = value
        }

    override fun damage(amount: Double) {
        if (amount >= health) health = 0.0

        health -= amount
    }

    override fun tick() {
        // TODO: Check for damage causes etc
        TODO("Not yet implemented")
    }
}

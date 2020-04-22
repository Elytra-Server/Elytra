package io.elytra.api.utils

import io.elytra.api.entity.Entity

/**
 * Represents an [Entity] that has health and can take damage.
 */
interface Damageable {

    var health: Double

    /**
     * Deals the given amount of damage to this entity.
     *
     * @param [amount] Amount of damage to deal
     */
    fun damage(amount: Double)
}

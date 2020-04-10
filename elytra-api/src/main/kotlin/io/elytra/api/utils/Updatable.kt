package io.elytra.api.utils

/**
 * Marks an actor [A] as updatable
 */
interface Updatable<A> {
    fun update(record: A)
}

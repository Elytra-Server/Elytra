package io.elytra.api.utils

/**
 * Marks a class as tickable, meaning it will run like a while(true)
 */
interface Tickable {

    fun tick()
}

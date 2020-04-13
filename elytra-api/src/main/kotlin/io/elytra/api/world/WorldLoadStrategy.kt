package io.elytra.api.world

/**
 * Represents a loading strategy for the worlds
 */
interface WorldLoadStrategy {

    fun load(path: String): World
}

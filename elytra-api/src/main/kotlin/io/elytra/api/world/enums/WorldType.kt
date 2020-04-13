package io.elytra.api.world.enums

/**
 * Represents the vanilla minecraft world type
 */
enum class WorldType(val id: Int, val prettyName: String) {
    NORMAL(0, "normal"),
    FLAT(1, "flat"),
    NETHER(2, "nether"),
    THE_END(3, "end"),
    DEBUG(4, "test")
}

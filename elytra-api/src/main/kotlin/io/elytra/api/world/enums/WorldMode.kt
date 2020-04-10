package io.elytra.api.world.enums

/**
 * Represents a type of write/read ability of a world
 */
enum class WorldMode(val compact: String) {
    READ_WRITE("rw"),
    READ_ONLY("ro")
}

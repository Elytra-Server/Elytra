package io.elytra.api.entity

import io.elytra.api.world.Position
import io.elytra.api.world.World

interface Entity {

    var world: World
    var position: Position
}

enum class EntityState {
    IDLE,
    IMMOBILE,
    SNEAKING,
    SWIMMING,
    SPRINTING,
    GLIDING,
    CLIMBING
}

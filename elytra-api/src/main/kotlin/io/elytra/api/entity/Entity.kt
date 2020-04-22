package io.elytra.api.entity

import io.elytra.api.utils.Tickable
import io.elytra.api.world.Position
import io.elytra.api.world.World

/**
 * Represents a in-game entity such as NPCs, Monsters, Players
 */
interface Entity : Tickable {

    var world: World

    var position: Position
}

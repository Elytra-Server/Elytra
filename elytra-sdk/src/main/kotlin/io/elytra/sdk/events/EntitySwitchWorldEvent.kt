package io.elytra.sdk.events

import io.elytra.api.entity.Entity
import io.elytra.api.events.ElytraEvent
import io.elytra.api.world.Position
import io.elytra.api.world.World

class EntitySwitchWorldEvent(
    val entity: Entity,
    var position: Position,
    var world: World
) : ElytraEvent

package io.elytra.sdk.events

import io.elytra.api.entity.Entity
import io.elytra.api.events.ElytraEvent
import io.elytra.api.world.Position

class EntityTeleportEvent (val entity: Entity,
						   var position: Position) : ElytraEvent

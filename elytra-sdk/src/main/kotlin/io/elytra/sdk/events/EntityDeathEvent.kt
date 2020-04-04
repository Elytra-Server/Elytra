package io.elytra.sdk.events

import io.elytra.api.entity.Entity
import io.elytra.api.events.ElytraEvent

class EntityDeathEvent(val entity: Entity) : ElytraEvent

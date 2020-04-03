package io.elytra.sdk.events

import io.elytra.api.entity.Player
import io.elytra.api.events.ElytraEvent

class PlayerQuitEvent(
    val player: Player,
    var message: String
) : ElytraEvent

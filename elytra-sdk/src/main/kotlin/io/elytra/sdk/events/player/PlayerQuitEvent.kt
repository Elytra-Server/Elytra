package io.elytra.sdk.events.player

import io.elytra.api.entity.player.Player
import io.elytra.api.events.ElytraEvent

class PlayerQuitEvent(
    val player: Player,
    var message: String
) : ElytraEvent

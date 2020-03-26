package io.elytra.api.events

import io.elytra.api.entity.Player
import io.elytra.api.io.ElytraEvent

class PlayerQuitEvent (player: Player, quitMessage: String) : ElytraEvent

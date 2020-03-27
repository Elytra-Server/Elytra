package io.elytra.sdk.events

import io.elytra.api.entity.Player
import io.elytra.api.events.ElytraEvent

class PlayerQuitEvent (player: Player, quitMessage: String) : ElytraEvent

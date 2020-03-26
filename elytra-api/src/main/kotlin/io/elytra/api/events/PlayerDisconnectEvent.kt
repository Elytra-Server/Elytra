package io.elytra.api.events

import io.elytra.api.entity.Player
import io.elytra.api.io.ElytraEvent

class PlayerDisconnectEvent(val player: Player, val reason: String) : ElytraEvent

package io.elytra.sdk.events

import io.elytra.api.entity.Player
import io.elytra.api.events.ElytraEvent

class PlayerDisconnectEvent(val player: Player, val reason: String) : ElytraEvent
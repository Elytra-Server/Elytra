package io.inb.api.events

import io.inb.api.entity.Player
import io.inb.api.io.ElytraEvent

class PlayerQuitEvent (player: Player, quitMessage: String) : ElytraEvent

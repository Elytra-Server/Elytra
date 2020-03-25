package io.inb.api.events

import io.inb.api.entity.Player
import io.inb.api.io.InbEvent

class PlayerQuitEvent (player: Player, quitMessage: String) : InbEvent

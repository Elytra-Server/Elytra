package io.inb.api.events

import io.inb.api.entity.Player
import io.inb.api.io.InbEvent

class PlayerQuitEvent (inbPlayer: Player, quitMessage: String) : InbEvent

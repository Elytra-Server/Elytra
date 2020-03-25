package io.inb.api.events

import io.inb.api.entity.Player
import io.inb.api.io.InbEvent

class PlayerDisconnectEvent(val player: Player, val reason: String) : InbEvent

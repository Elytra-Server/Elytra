package io.inb.api.events

import io.inb.api.io.InbEvent
import io.inb.api.utils.motd.Motd

data class ServerListPingEvent(
	val motd: Motd
) : InbEvent

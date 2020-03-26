package io.inb.api.events

import io.inb.api.io.ElytraEvent
import io.inb.api.server.motd.Motd

data class ServerListPingEvent(
	val motd: Motd
) : ElytraEvent

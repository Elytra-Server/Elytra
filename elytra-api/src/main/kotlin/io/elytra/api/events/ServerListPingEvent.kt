package io.elytra.api.events

import io.elytra.api.io.ElytraEvent
import io.elytra.api.server.motd.Motd

data class ServerListPingEvent(
	val motd: Motd
) : ElytraEvent

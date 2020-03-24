package io.inb.api.events

import io.inb.api.io.InbEvent

data class ServerListPingEvent(
	val motd: String,
	val version: String,
	val protocolVersion: Int,
	val numPlayer: Int
) : InbEvent

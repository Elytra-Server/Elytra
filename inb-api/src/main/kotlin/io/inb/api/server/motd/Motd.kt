package io.inb.api.server.motd

data class Motd (
	val description: String,
	val pingText: String,
	val maxPlayers: Int
)

package io.inb.api.utils.motd

data class Motd (
	val description: String,
	val version: String,
	val versionText: String,
	val protocolVersion: Int,
	val numPlayers: Int
)

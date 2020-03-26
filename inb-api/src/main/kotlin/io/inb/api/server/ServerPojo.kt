package io.inb.api.server

import io.inb.api.utils.motd.Motd

data class WorldPojo(
	val initialWorld: String,
	val renderDistance: Int,
	val disableMobs: Boolean
)

data class ServerOptionsPojo(
	val maxPlayers: Int,
	val onlineMode: Boolean,
	val eulaCheck: Boolean
)

data class ServerPojo(var motd: Motd, val options: ServerOptionsPojo, val world: WorldPojo)

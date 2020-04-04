package io.elytra.api.server

import io.elytra.api.server.motd.Motd

data class WorldPojo(
    val initialWorld: String = "world",
    val renderDistance: Int = 20,
    val disableMobs: Boolean = false
)

data class ServerOptionsPojo(
    val port: Int = 25565,
    val maxPlayers: Int = 60,
    val onlyPremium: Boolean = true,
    val eulaCheck: Boolean = false
)

/**
 * Describes the server settings setted on the file server.json
 */
data class ServerDescriptor(
    var motd: Motd,
    val options: ServerOptionsPojo,
    val world: WorldPojo
)

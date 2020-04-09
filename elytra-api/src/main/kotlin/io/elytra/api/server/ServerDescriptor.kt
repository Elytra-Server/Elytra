package io.elytra.api.server

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

data class Motd(
    val description: String = "A minecraft server",
    val pingText: String = "Elytra 1.15.2"
)

/**
 * Describes the server settings setted on the file server.json
 */
data class ServerDescriptor(
    val options: ServerOptionsPojo,
    val world: WorldPojo,
    var motd: Motd
)

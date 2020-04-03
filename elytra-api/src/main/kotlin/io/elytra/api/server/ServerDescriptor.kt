package io.elytra.api.server

import io.elytra.api.server.motd.Motd

data class WorldPojo(
    val initialWorld: String,
    val renderDistance: Int,
    val disableMobs: Boolean
)

data class ServerOptionsPojo(
    val maxPlayers: Int,
    val onyPremium: Boolean,
    val eulaCheck: Boolean
)

/**
 * Describes the server settings setted on the file server.json
 */
data class ServerDescriptor(
    var motd: Motd,
    val options: ServerOptionsPojo,
    val world: WorldPojo
)

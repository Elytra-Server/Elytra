package io.elytra.api.server

data class WorldPojo(
    val initialWorld: String = "world",
    val renderDistance: Int = 20,
    val disableMobs: Boolean = false
) {
    fun builder() = Builder(initialWorld, renderDistance, disableMobs)

    data class Builder(
        var initialWorld: String = "world",
        var renderDistance: Int = 20,
        var disableMobs: Boolean = false
    ) {
        fun build() = WorldPojo(initialWorld, renderDistance, disableMobs)
    }
}

data class ServerOptionsPojo(
    val port: Int = 25565,
    val maxPlayers: Int = 60,
    val onlyPremium: Boolean = true,
    val eulaCheck: Boolean = false
) {
    fun builder() = Builder(port, maxPlayers, onlyPremium, eulaCheck)

    data class Builder(
        var port: Int = 25565,
        var maxPlayers: Int = 60,
        var onlyPremium: Boolean = true,
        var eulaCheck: Boolean = false
    ) {
        fun build() = ServerOptionsPojo(port, maxPlayers, onlyPremium, eulaCheck)
    }
}

data class Motd(
    val description: String = "A minecraft server",
    val pingText: String = "Elytra 1.15.2"
) {
    fun builder() = Builder(description, pingText)

    data class Builder(
        var description: String = "A minecraft server",
        var pingText: String = "Elytra 1.15.2"
    ) {
        fun build() = Motd(description, pingText)
    }
}

/**
 * Describes the server settings setted on the file server.json
 */
data class ServerDescriptor(
    val options: ServerOptionsPojo,
    val world: WorldPojo,
    var motd: Motd
) {
    fun builder() = Builder(options.builder(), world.builder(), motd.builder())

    data class Builder(
        var options: ServerOptionsPojo.Builder = ServerOptionsPojo.Builder(),
        var world: WorldPojo.Builder = WorldPojo.Builder(),
        var motd: Motd.Builder = Motd.Builder()
    ) {
        fun build() = ServerDescriptor(options.build(), world.build(), motd.build())
    }
}

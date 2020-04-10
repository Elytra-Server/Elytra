package io.elytra.api.world.enums

/**
 * Represents the type of gamemode the player is in
 *
 * @param [value] gamemode value alias
 * @param [aliases] aliases of the gamemode name
 */
enum class GameMode(val value: Int, val aliases: List<String>) {

    SURVIVAL(0, listOf("s")),
    CREATIVE(1, listOf("c")),
    ADVENTURE(2, listOf("a")),
    SPECTATOR(3, listOf("spec"));

    companion object {

        fun get(string: String): GameMode? {
            var gamemode: GameMode? = getByName(string)

            if (gamemode == null) {
                val int: Int? = string.toIntOrNull()
                if (int != null) {
                    gamemode = getByIdValue(int)
                }
            }

            if (gamemode == null) {
                gamemode = getByAlias(string)
            }

            return gamemode
        }

        fun getByName(name: String): GameMode? = values().firstOrNull { gameMode ->
            gameMode.name.equals(name, ignoreCase = true)
        }

        fun getByIdValue(value: Int): GameMode? = values().firstOrNull { gameMode ->
            gameMode.value == value
        }

        fun getByAlias(alias: String): GameMode? = values().firstOrNull { gameMode ->
            gameMode.aliases.any { s -> s.equals(alias, ignoreCase = true) }
        }
    }
}

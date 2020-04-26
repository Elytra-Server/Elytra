package io.elytra.api.server

import io.elytra.api.chat.TextComponent
import io.elytra.api.entity.player.Player
import io.elytra.api.utils.get
import java.util.*
import org.slf4j.Logger

/**
 * [Server] is the game entry that managers the
 * game loop.
 */
interface Server {
    companion object : Server by get()

    val serverDescriptor: ServerDescriptor
    val logger: Logger

    val onlinePlayers: Set<Player>

    fun getPlayer(uuid: UUID): Player?

    fun getPlayer(name: String): Player?

    fun isPlayerOnline(uuid: UUID): Boolean

    fun isPlayerOnline(name: String): Boolean

    /**
     * Sends a chat message to all online players
     *
     * @param message chat message to send
     */
    fun broadcastMessage(message: String)

    /**
     * Sends a chat message to all online players who
     * matches the filter.
     *
     * @param message chat message to send
     * @param filter filter in order to send
     */
    fun broadcastMessage(message: String, filter: (player: Player) -> Boolean)

    fun broadcastMessage(message: TextComponent)

    fun broadcastMessage(message: TextComponent, filter: (player: Player) -> Boolean)
}

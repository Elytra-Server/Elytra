package io.elytra.api.server

import io.elytra.api.entity.Player


/**
 * [Server] is the game entry that managers the
 * game loop.
 */
interface Server {
	var serverDescriptor: ServerDescriptor

	/**
	 * Initializes the execution of the game loop and related services
	 */
	fun boot()

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
}

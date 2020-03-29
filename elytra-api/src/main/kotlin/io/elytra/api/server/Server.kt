package io.elytra.api.server


/**
 * [Server] is the game entry that managers the
 * game loop.
 */
interface Server {
	var serverDescriptor: ServerDescriptor?

	/**
	 * Initializes the execution of the game loop and related services
	 */
	fun boot()

	/**
	 * Sends a chat message to all online players
	 */
	fun broadcastMessage(message: String)
}

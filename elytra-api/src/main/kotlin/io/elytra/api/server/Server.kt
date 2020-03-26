package io.elytra.api.server


/**
 * [Server] is the game entry that managers the
 * game loop.
 */
interface Server {
	var serverDescriptor: ServerPojo?

	/**
	 * Initializes the execution of the game loop and related services
	 */
	fun boot()
}

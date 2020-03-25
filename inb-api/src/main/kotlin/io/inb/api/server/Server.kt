package io.inb.api.server

import io.inb.api.network.SessionRegistry

/**
 * [Server] is the game entry that managers the
 * game loop.
 */
interface Server {
	val sessionRegistry: SessionRegistry

	/**
	 * Initializes the execution of the game loop and related services
	 */
	fun boot()
}

package io.inb.api.server

import io.inb.api.network.SessionRegistry
import io.inb.api.utils.motd.Motd

/**
 * [Server] is the game entry that managers the
 * game loop.
 */
interface Server {
	val sessionRegistry: SessionRegistry
	var motd: Motd?

	/**
	 * Initializes the execution of the game loop and related services
	 */
	fun boot()
}

package io.inb.api.server

import com.flowpowered.network.session.BasicSession
import io.inb.api.registry.Registry


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

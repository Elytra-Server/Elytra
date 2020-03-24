package io.inb.api

import io.inb.api.network.InbNetworkServer
import io.inb.api.network.SessionRegistry

/**
 * The core server class of the Inb server.
 *
 * @author Graham AstroCoder
 */
class InbServer(
	private val port: Int = 25565,
	val sessionRegistry: SessionRegistry = SessionRegistry()
) {
	companion object {
		fun getServer() = InbServer()

		/**
		 * The game version supported by the server.
		 */
		const val GAME_VERSION = "14.4.4"

		/**
		 * The protocol version supported by the server.
		 */
		const val PROTOCOL_VERSION = 498
	}

	fun run(){
		InbNetworkServer(port).start()
	}
}


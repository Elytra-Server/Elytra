package io.inb.api

import io.inb.api.network.NetworkServer
import io.inb.api.network.SessionRegistry
import io.inb.api.network.protocol.PacketProvider

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
		const val GAME_VERSION = "12.2.2"

		/**
		 * The protocol version supported by the server.
		 */
		const val PROTOCOL_VERSION = 340

	}

	fun run(){
		PacketProvider()
		NetworkServer(port).start()
	}


}


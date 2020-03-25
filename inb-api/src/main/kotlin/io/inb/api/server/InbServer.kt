package io.inb.api.server

import io.inb.api.network.NetworkServer
import io.inb.api.network.SessionRegistry
import io.inb.api.network.protocol.PacketProvider
import io.inb.api.scheduler.Scheduler
import io.inb.api.utils.motd.Motd

class InbServer(
	override val sessionRegistry: SessionRegistry = SessionRegistry(),
	override var motd: Motd? = null,

	private val port: Int = 25565,
	private val scheduler: Scheduler = Scheduler(sessionRegistry)
) : Server {

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

	override fun boot(){
		PacketProvider()
		scheduler.start()

		bindNetwork()
	}

	private fun bindNetwork(){
		NetworkServer(port).start()
	}
}


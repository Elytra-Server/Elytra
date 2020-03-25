package io.inb.api.server

import com.google.gson.Gson
import io.inb.api.events.ServerListPingEvent
import io.inb.api.io.EventBus
import io.inb.api.network.NetworkServer
import io.inb.api.network.SessionRegistry
import io.inb.api.network.protocol.PacketProvider
import io.inb.api.scheduler.Scheduler
import io.inb.api.utils.formatting.Formatting
import io.inb.api.utils.motd.Motd
import java.io.IOException
import java.io.Reader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


class InbServer(
	override val sessionRegistry: SessionRegistry = SessionRegistry(),
	override var motd: Motd? = null,

	private val port: Int = 25565,
	private val scheduler: Scheduler = Scheduler(sessionRegistry)
) : Server {

	companion object {
		fun getServer() : Server = InbServer()

		/**
		 * The game version supported by the server.
		 */
		const val GAME_VERSION = "12.2.2"

		/**
		 * The protocol version supported by the server.
		 */
		const val PROTOCOL_VERSION = 340
	}

	init {
		loadConfigs()
	}

	override fun boot() {
		PacketProvider()
		scheduler.start()

		bindNetwork()
	}

	//TODO: Will be refactored, just for testing for now
	private fun loadConfigs() {
		try {
			val gson = Gson()

			val resource = javaClass.classLoader.getResource("./server.json")

			val serverPojo: ServerPojo = gson.fromJson(resource.readText(), ServerPojo::class.java)
			val (motd) = serverPojo

			this.motd = motd
		} catch (e: IOException) {
			println(e.printStackTrace())
		}
	}

	private fun bindNetwork() {
		NetworkServer(port).start()
	}
}


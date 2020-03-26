package io.inb.api.server

import com.google.gson.Gson
import io.inb.api.events.PlayerDisconnectEvent
import io.inb.api.io.EventBus
import io.inb.api.io.listen
import io.inb.api.network.NetworkServer
import io.inb.api.network.SessionRegistry
import io.inb.api.network.protocol.PacketProvider
import io.inb.api.scheduler.Scheduler
import io.inb.api.utils.motd.Motd
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import java.lang.System.getLogger
import java.util.logging.Level

class InbServer(
	override val sessionRegistry: SessionRegistry = SessionRegistry(),
	override var serverDescriptor: ServerPojo? = null,

	private val port: Int = 25565,
	private val scheduler: Scheduler = Scheduler(sessionRegistry)
) : Server {

	companion object {
		val logger: Logger = LoggerFactory.getLogger("INB")
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

			this.serverDescriptor = serverPojo
		} catch (e: IOException) {
			println(e.printStackTrace())
		}
	}

	private fun bindNetwork() {
		NetworkServer(port, sessionRegistry).start()
	}
}


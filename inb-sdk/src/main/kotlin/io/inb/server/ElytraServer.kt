package io.inb.server

import com.google.gson.Gson
import io.inb.api.registry.Registry
import io.inb.network.NetworkServer
import io.inb.network.SessionRegistry
import io.inb.network.protocol.PacketProvider
import io.inb.scheduler.Scheduler
import io.inb.api.server.Server
import io.inb.api.server.ServerPojo
import io.inb.network.NetworkSession
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException

class ElytraServer(
	override var serverDescriptor: ServerPojo? = null,
	private val port: Int = 25565,
	private val sessionRegistry: SessionRegistry = SessionRegistry(),
	private val scheduler: Scheduler = Scheduler(sessionRegistry)
) : Server {

	companion object {
		val logger: Logger = LoggerFactory.getLogger("INB")
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


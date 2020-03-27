package io.elytra.sdk.server

import com.google.gson.Gson
import io.elytra.sdk.network.NetworkServer
import io.elytra.sdk.network.SessionRegistry
import io.elytra.sdk.network.protocol.PacketProvider
import io.elytra.sdk.scheduler.Scheduler
import io.elytra.api.server.Server
import io.elytra.api.server.ServerPojo
import io.elytra.sdk.console.ElytraConsole
import java.io.IOException

class Elytra private constructor(
	override var serverDescriptor: ServerPojo? = null,
	private val port: Int = 25565,
	private val sessionRegistry: SessionRegistry = SessionRegistry(),
	private val scheduler: Scheduler = Scheduler(sessionRegistry)
) : Server {

	companion object {
		val server: Server = Elytra()
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

			ElytraConsole.send(
				StringBuilder("&2").append("Server configurations loaded.")
			)
		} catch (e: IOException) {
			println(e.printStackTrace())
		}
	}

	private fun bindNetwork() {
		NetworkServer(port, sessionRegistry).start()
	}
}


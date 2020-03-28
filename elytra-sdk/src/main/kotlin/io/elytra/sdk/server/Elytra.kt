package io.elytra.sdk.server

import io.elytra.api.io.ConsoleSender
import io.elytra.api.server.Server
import io.elytra.api.server.ServerDescriptor
import io.elytra.sdk.config.ServerConfigFile
import io.elytra.sdk.console.ElytraConsole
import io.elytra.sdk.network.NetworkServer
import io.elytra.sdk.network.SessionRegistry
import io.elytra.sdk.network.protocol.PacketProvider
import io.elytra.sdk.scheduler.Scheduler
import org.slf4j.LoggerFactory

class Elytra private constructor(
	override var serverDescriptor: ServerDescriptor? = null,
	val playerRegistry: PlayerRegistry = PlayerRegistry(),
	private val port: Int = 25565,
	private val sessionRegistry: SessionRegistry = SessionRegistry(),
	private val scheduler: Scheduler = Scheduler(sessionRegistry)
) : Server {

	companion object {
		val server = Elytra()
		val console: ConsoleSender = ElytraConsole(LoggerFactory.getLogger("Elytra"))
	}

	init {
		loadConfigs()
	}

	override fun boot() {
		PacketProvider()
		scheduler.start()

		loadConfigs()
		bindNetwork()
	}

	//TODO: Will be refactored, just for testing for now
	private fun loadConfigs() {
		val serverConfigFile = ServerConfigFile(serverDescriptor)
		serverConfigFile.load()
	}

	private fun bindNetwork() {
		NetworkServer(port, sessionRegistry).start()
	}
}


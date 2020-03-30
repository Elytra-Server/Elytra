package io.elytra.sdk.server

import com.flowpowered.network.Message
import com.mojang.authlib.minecraft.MinecraftSessionService
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
import io.elytra.api.entity.Player
import io.elytra.api.io.ConsoleSender
import io.elytra.api.server.Server
import io.elytra.api.server.ServerDescriptor
import io.elytra.sdk.config.ServerConfigFile
import io.elytra.sdk.console.ElytraConsole
import io.elytra.sdk.entity.ElytraPlayer
import io.elytra.sdk.network.NetworkServer
import io.elytra.sdk.network.SessionRegistry
import io.elytra.sdk.network.protocol.PacketProvider
import io.elytra.sdk.scheduler.Scheduler
import io.elytra.sdk.network.utils.cryptManager
import org.slf4j.LoggerFactory
import java.net.Proxy
import java.security.KeyPair
import java.util.*

class Elytra private constructor(
	override var serverDescriptor: ServerDescriptor? = null,
	val sessionService: MinecraftSessionService = (YggdrasilAuthenticationService(
		Proxy.NO_PROXY, UUID.randomUUID().toString()
	)).createMinecraftSessionService(),
	val playerRegistry: PlayerRegistry = PlayerRegistry(),
	val sessionRegistry: SessionRegistry = SessionRegistry(),
	val keypair: KeyPair = cryptManager.generateKeyPair(),
	val debug: Boolean = true,
	private val port: Int = 25565,
	private val scheduler: Scheduler = Scheduler(sessionRegistry)
) : Server {

	companion object {
		val server = Elytra()
		val console: ConsoleSender = ElytraConsole(LoggerFactory.getLogger("Elytra"))

		fun players(): Iterator<Player>  = server.playerRegistry.iterator()

		fun sendPacketToAll(message: Message){
			for (player in players()) {
				(player as ElytraPlayer).sendPacket(message)
			}
		}
	}

	init {
		loadConfigs()
	}

	override fun boot() {
		PacketProvider()
		scheduler.start()

		NetworkServer(port, sessionRegistry).start()
	}

	override fun broadcastMessage(message: String) {
		broadcastMessage(message) { it.online }
	}

	override fun broadcastMessage(message: String, filter: (player: Player) -> Boolean) {
		for (player in players()) {
			if(filter.invoke(player)) player.sendMessage(message)
		}
	}


	//TODO: Will be refactored, just for testing for now
	private fun loadConfigs() {
		ServerConfigFile(serverDescriptor)
	}
}


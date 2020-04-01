package io.elytra.sdk.server

import ch.qos.logback.classic.util.ContextInitializer
import com.flowpowered.network.Message
import com.mojang.authlib.minecraft.MinecraftSessionService
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
import io.elytra.api.command.handler.CommandHandler
import io.elytra.api.command.registry.CommandRegistry
import io.elytra.api.entity.Player
import io.elytra.api.io.ConsoleSender
import io.elytra.api.server.Server
import io.elytra.api.server.ServerDescriptor
import io.elytra.sdk.command.handler.ElytraCommandHandler
import io.elytra.sdk.command.registry.ElytraCommandRegistry
import io.elytra.sdk.entity.ElytraPlayer
import io.elytra.sdk.io.ElytraConsole
import io.elytra.sdk.io.config.JsonConfigurationFile
import io.elytra.sdk.network.NetworkServer
import io.elytra.sdk.network.SessionRegistry
import io.elytra.sdk.network.protocol.PacketProvider
import io.elytra.sdk.network.utils.cryptManager
import io.elytra.sdk.scheduler.Scheduler
import io.elytra.sdk.utils.ElytraConsts
import io.elytra.sdk.utils.ResourceUtils
import org.slf4j.LoggerFactory
import java.net.Proxy
import java.security.KeyPair
import java.util.*

class Elytra private constructor(
	val sessionService: MinecraftSessionService = (YggdrasilAuthenticationService(
		Proxy.NO_PROXY, UUID.randomUUID().toString()
	)).createMinecraftSessionService(),
	val playerRegistry: PlayerRegistry = PlayerRegistry(),
	val sessionRegistry: SessionRegistry = SessionRegistry(),
	val commandRegistry: CommandRegistry = ElytraCommandRegistry(),
	val commandHandler: CommandHandler = ElytraCommandHandler(commandRegistry),
	val keypair: KeyPair = cryptManager.generateKeyPair(),
	val debug: Boolean = false,
	private val port: Int = 25565,
	private val scheduler: Scheduler = Scheduler(sessionRegistry)
) : Server {
	override lateinit var serverDescriptor: ServerDescriptor

	companion object {
		val server = Elytra()
		val console: ConsoleSender = ElytraConsole(LoggerFactory.getLogger("Elytra"))

		fun players(): Iterator<Player> = server.playerRegistry.iterator()

		fun sendPacketToAll(message: Message) {
			for (player in players()) {
				(player as ElytraPlayer).sendPacket(message)
			}
		}
	}

	override fun boot() {
		System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, "./logback.xml")

		loadConfigs()
		PacketProvider()
		scheduler.start()

		NetworkServer(port, sessionRegistry).start()
	}

	override fun broadcastMessage(message: String) {
		broadcastMessage(message) { it.online }
	}

	override fun broadcastMessage(message: String, filter: (player: Player) -> Boolean) {
		for (player in players()) {
			if (filter.invoke(player)) player.sendMessage(message)
		}
	}

	private fun loadConfigs() {
		ResourceUtils.saveResource("server.json", ElytraConsts.SERVER_CONFIG_PATH, false)

		serverDescriptor = JsonConfigurationFile.getConfig(ElytraConsts.SERVER_CONFIG_PATH)
	}
}


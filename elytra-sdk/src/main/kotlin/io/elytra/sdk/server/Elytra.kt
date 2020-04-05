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
import io.elytra.sdk.events.TemporaryEventRegister
import io.elytra.sdk.io.ElytraConsole
import io.elytra.sdk.io.config.JsonConfigurationFile
import io.elytra.sdk.network.NetworkServer
import io.elytra.sdk.network.SessionRegistry
import io.elytra.sdk.network.protocol.PacketProvider
import io.elytra.sdk.network.protocol.ProtocolInfo
import io.elytra.sdk.network.utils.cryptManager
import io.elytra.sdk.scheduler.Scheduler
import io.elytra.sdk.utils.ElytraConsts
import io.elytra.sdk.utils.ResourceUtils
import io.elytra.sdk.world.strategy.AnvilWorldStrategy
import java.net.BindException
import java.net.Proxy
import java.security.KeyPair
import java.time.Instant
import java.util.*
import kotlin.system.exitProcess
import org.slf4j.LoggerFactory

class Elytra private constructor(
    val sessionService: MinecraftSessionService = (YggdrasilAuthenticationService(
        Proxy.NO_PROXY, UUID.randomUUID().toString()
    )).createMinecraftSessionService(),
    val playerRegistry: PlayerRegistry = PlayerRegistry(),
    val sessionRegistry: SessionRegistry = SessionRegistry(),
    val keypair: KeyPair = cryptManager.generateKeyPair(),
    val debug: Boolean = true,
    private val commandRegistry: CommandRegistry = ElytraCommandRegistry(),
    val commandHandler: CommandHandler = ElytraCommandHandler(commandRegistry),
    private val scheduler: Scheduler = Scheduler(sessionRegistry),
    val startedAt: Instant = Instant.now()
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
        try {
            System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, "./logback.xml")

            console.info("Loading server configuration")
            loadConfigs()

            console.info("Bootstrapping the server...")
            console.info("This version of Elytra is targeted for Minecraft ${ProtocolInfo.MINECRAFT_VERSION}")
            PacketProvider()
            scheduler.start()

            TemporaryEventRegister().register()
            AnvilWorldStrategy().load(javaClass.classLoader.getResource("bitch").path)
            NetworkServer(serverDescriptor.options.port, sessionRegistry).start()
        } catch (e: BindException) {
            console.info(" ")
            console.error("&c     FAILED TO BIND TO PORT")
            console.error(" ")
            console.error("&cThe port ${serverDescriptor.options.port} is already in use.")
            exitProcess(1)
        }
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

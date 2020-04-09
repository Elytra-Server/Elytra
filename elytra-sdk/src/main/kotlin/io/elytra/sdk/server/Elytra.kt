package io.elytra.sdk.server

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
import io.elytra.sdk.world.ElytraWorld
import io.elytra.sdk.world.strategy.ClassicWorldStrategy
import java.net.BindException
import java.net.Proxy
import java.security.KeyPair
import java.util.*
import kotlin.system.exitProcess
import org.slf4j.LoggerFactory

class Elytra : Server {

    private val commandRegistry: CommandRegistry = ElytraCommandRegistry()

    val playerRegistry: PlayerRegistry = PlayerRegistry()

    val sessionRegistry: SessionRegistry = SessionRegistry()

    private val scheduler: Scheduler = Scheduler(sessionRegistry)

    override lateinit var serverDescriptor: ServerDescriptor

    lateinit var mainWorld: ElytraWorld

    val keypair: KeyPair = cryptManager.generateKeyPair()

    val commandHandler: CommandHandler = ElytraCommandHandler(commandRegistry)

    val debug: Boolean = false

    val sessionService: MinecraftSessionService = (YggdrasilAuthenticationService(
        Proxy.NO_PROXY, UUID.randomUUID().toString()
    )).createMinecraftSessionService()

    companion object {
        val server = Elytra()
        val console: ConsoleSender = ElytraConsole(LoggerFactory.getLogger("Elytra"))

        fun players(): Iterator<Player> = server.playerRegistry.iterator()

        fun player(username: String): Player? = server.playerRegistry.get(username)

        // Rename pls
        fun online(username: String): Boolean = server.playerRegistry.has(username)

        fun sendPacketToAll(message: Message) {
            for (player in players()) {
                (player as ElytraPlayer).sendPacket(message)
            }
        }
    }

    override fun boot() {
        try {
            console.info("Loading server configuration")
            loadConfigs()

            console.info("Bootstrapping the server...")
            console.info("This version of Elytra is targeted for Minecraft ${ProtocolInfo.MINECRAFT_VERSION}")
            PacketProvider()
            scheduler.start()

            TemporaryEventRegister().register()
            mainWorld = ClassicWorldStrategy().load(javaClass.classLoader.getResource("Test2.cw").path) as ElytraWorld
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

        val serverDescriptor: ServerDescriptor = JsonConfigurationFile.getConfig(ElytraConsts.SERVER_CONFIG_PATH)
        this.serverDescriptor = serverDescriptor.copy(motd = serverDescriptor.motd.copy(
            description = serverDescriptor.motd.description.replace('&', '§'),
            pingText = serverDescriptor.motd.pingText.replace('&', '§')
        ))

        JsonConfigurationFile.saveToConfig(serverDescriptor, ElytraConsts.SERVER_CONFIG_PATH)
    }
}

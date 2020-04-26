package io.elytra.sdk.server

import com.flowpowered.network.Message
import com.mojang.authlib.minecraft.MinecraftSessionService
import io.elytra.api.chat.ChatMode
import io.elytra.api.chat.TextComponent
import io.elytra.api.command.registry.CommandRegistry
import io.elytra.api.entity.player.Player
import io.elytra.api.io.i18n.I18n
import io.elytra.api.io.i18n.MessageBuilder
import io.elytra.api.server.Server
import io.elytra.api.server.ServerDescriptor
import io.elytra.sdk.entity.ElytraPlayer
import io.elytra.sdk.events.TemporaryEventRegister
import io.elytra.sdk.io.config.JsonConfigurationFile
import io.elytra.sdk.network.NetworkServer
import io.elytra.sdk.network.SessionRegistry
import io.elytra.sdk.network.protocol.PacketProvider
import io.elytra.sdk.network.protocol.ProtocolInfo
import io.elytra.sdk.network.protocol.message.play.outbound.OutboundChatMessage
import io.elytra.sdk.network.utils.CryptManager
import io.elytra.sdk.scheduler.Scheduler
import io.elytra.sdk.utils.ElytraConsts
import io.elytra.sdk.utils.ResourceUtils
import io.elytra.sdk.world.ElytraWorld
import io.elytra.sdk.world.strategy.ClassicWorldStrategy
import java.net.BindException
import java.security.KeyPair
import kotlin.system.exitProcess
import org.jetbrains.annotations.PropertyKey
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Elytra : Server, KoinComponent {
    val startedAt: Long = System.currentTimeMillis()
    val keypair: KeyPair = CryptManager.generateKeyPair()
    val debug: Boolean = false
    val playerRegistry: PlayerRegistry by inject()
    val sessionRegistry: SessionRegistry by inject()
    val commandRegistry: CommandRegistry by inject()
    val sessionService: MinecraftSessionService by inject()

    private val scheduler: Scheduler by inject()
    override lateinit var serverDescriptor: ServerDescriptor
    lateinit var mainWorld: ElytraWorld

    override fun boot() {
        try {
            printUglyLogo()
            console.info("Loading server configuration")
            loadConfigs()
            console.info("Bootstrapping the server...")
            console.info("This version of Elytra is targeted for Minecraft ${ProtocolInfo.MINECRAFT_VERSION}")

            PacketProvider()
            scheduler.start()

            ResourceUtils.saveResource("Test2.cw", "./Test2.cw", false)
            TemporaryEventRegister().register()
            mainWorld = ClassicWorldStrategy().load("./Test2.cw") as ElytraWorld
            NetworkServer(serverDescriptor.options.port, sessionRegistry).start()
        } catch (e: BindException) {
            console.info(" ")
            console.error("&c     FAILED TO BIND TO PORT")
            console.error(" ")
            console.error("&cThe port ${serverDescriptor.options.port} is already in use.")
            exitProcess(1)
        }
    }

    override fun broadcastMessage(message: String) = broadcastMessage(TextComponent(message))

    fun broadcastMessage(message: TextComponent) = broadcastMessage(message) { it.online }

    override fun broadcastMessage(message: String, filter: (player: Player) -> Boolean) =
        broadcastMessage(TextComponent(message), filter)

    fun broadcastMessage(component: TextComponent, filter: (player: Player) -> Boolean) {
        val packet = OutboundChatMessage(component, ChatMode.FEEDBACK)
        for (player in players()) {
            if (filter.invoke(player)) sendPacketToAll(packet)
        }
    }

    private fun loadConfigs() {
        ResourceUtils.saveResource("server.json", ElytraConsts.SERVER_CONFIG_PATH, false)

        val serverDescriptor: ServerDescriptor = JsonConfigurationFile.getConfig(ElytraConsts.SERVER_CONFIG_PATH)
        this.serverDescriptor = serverDescriptor.copy(motd = serverDescriptor.motd.copy(
            description = serverDescriptor.motd.description
                .replace('&', '§')
                .replace("(?<!\\\\)\"".toRegex(), "\\\\\""),
            pingText = serverDescriptor.motd.pingText
                .replace('&', '§')
                .replace("(?<!\\\\)\"".toRegex(), "\\\\\"")
        ))

        JsonConfigurationFile.saveToConfig(serverDescriptor, ElytraConsts.SERVER_CONFIG_PATH)
    }

    private fun printUglyLogo() {
        println("""
 _____  _         _               
|  ___|| |       | |              
| |__  | | _   _ | |_  _ __  __ _ 
|  __| | || | | || __|| '__|/ _` |
| |___ | || |_| || |_ | |  | (_| |
\____/ |_| \__, | \__||_|   \__,_|
            __/ |                 
           |___/                               
            """)
    }

    companion object {
        val server = Elytra()
        val console: Logger = LoggerFactory.getLogger("Elytra")

        fun players(): Iterator<Player> = server.playerRegistry.iterator()

        fun player(username: String): Player? = server.playerRegistry.get(username)

        // Rename pls
        fun online(username: String): Boolean = server.playerRegistry.has(username)

        fun sendPacketToAll(message: Message) {
            for (player in players()) {
                (player as ElytraPlayer).sendPacket(message)
            }
        }

        fun broadcastMessage(message: String) = server.broadcastMessage(message)

        fun broadcastMessage(message: TextComponent) = server.broadcastMessage(message)

        fun broadcast(@PropertyKey(resourceBundle = I18n.BUNDLE_BASE_NAME) messageKey: String, builder: MessageBuilder.() -> Unit = {}) {
            val message = MessageBuilder(messageKey).apply(builder).getOrBuild()

            server.broadcastMessage(message)
        }
    }
}

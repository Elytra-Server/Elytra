package io.elytra.sdk.server

import com.flowpowered.network.Message
import com.mojang.authlib.minecraft.MinecraftSessionService
import io.elytra.api.chat.ChatMode
import io.elytra.api.chat.TextComponent
import io.elytra.api.command.registry.CommandRegistry
import io.elytra.api.entity.player.Player
import io.elytra.api.server.ServerDescriptor
import io.elytra.api.utils.inject
import io.elytra.sdk.entity.ElytraPlayer
import io.elytra.sdk.events.TemporaryEventRegister
import io.elytra.sdk.network.NetworkServer
import io.elytra.sdk.network.SessionRegistry
import io.elytra.sdk.network.protocol.PacketProvider
import io.elytra.sdk.network.protocol.message.play.outbound.OutboundChatMessage
import io.elytra.sdk.network.utils.CryptManager
import io.elytra.sdk.scheduler.Scheduler
import io.elytra.sdk.utils.ResourceUtils
import io.elytra.sdk.world.ElytraWorld
import io.elytra.sdk.world.strategy.ClassicWorldStrategy
import java.net.BindException
import java.security.KeyPair
import java.util.*
import kotlin.system.exitProcess
import org.koin.core.KoinComponent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Elytra(
    override val serverDescriptor: ServerDescriptor
) : ElytraServer {
    // region Fields
    val startedAt: Long = System.currentTimeMillis()
    override val logger: Logger = LoggerFactory.getLogger("Elytra")

    val keypair: KeyPair = CryptManager.generateKeyPair()
    val debug: Boolean = false
    val scheduler: Scheduler by inject()
    val playerRegistry: PlayerRegistry by inject()
    val sessionRegistry: SessionRegistry by inject()
    val commandRegistry: CommandRegistry by inject()
    val sessionService: MinecraftSessionService by inject()

    lateinit var mainWorld: ElytraWorld
    // endregion

    fun boot() {
        try {
            PacketProvider()
            scheduler.start()

            TemporaryEventRegister().register()

            ResourceUtils.saveResource("Test2.cw", "./Test2.cw", false)
            mainWorld = ClassicWorldStrategy().load("./Test2.cw") as ElytraWorld

            NetworkServer(serverDescriptor.options.port, sessionRegistry).start()
        } catch (e: BindException) {
            logger.info(" ")
            logger.error("&c     FAILED TO BIND TO PORT")
            logger.error(" ")
            logger.error("&cThe port ${serverDescriptor.options.port} is already in use.")
            exitProcess(1)
        }
    }

    override fun broadcastPacket(message: Message, filter: (player: Player) -> Boolean) =
        onlinePlayers.filter(filter).forEach { (it as ElytraPlayer).sendPacket(message) }

    override val onlinePlayers: Set<Player>
        get() = playerRegistry.getAll()

    override fun getPlayer(uuid: UUID): Player? = onlinePlayers.firstOrNull { it.gameProfile.id == uuid }

    override fun getPlayer(name: String): Player? = onlinePlayers.firstOrNull { it.gameProfile.name == name }

    override fun isPlayerOnline(uuid: UUID): Boolean = onlinePlayers.any { it.gameProfile.id == uuid }

    override fun isPlayerOnline(name: String): Boolean = onlinePlayers.any { it.gameProfile.name == name }

    override fun broadcastMessage(message: String) = broadcastMessage(TextComponent(message))

    override fun broadcastMessage(message: TextComponent) = broadcastMessage(message) { it.online }

    override fun broadcastMessage(message: String, filter: (player: Player) -> Boolean) =
        broadcastMessage(TextComponent(message), filter)

    override fun broadcastMessage(message: TextComponent, filter: (player: Player) -> Boolean) =
        broadcastPacket(OutboundChatMessage(message, ChatMode.FEEDBACK), filter)

    companion object : KoinComponent {
        val server: Elytra by inject()
        val logger: Logger get() = server.logger
    }
}

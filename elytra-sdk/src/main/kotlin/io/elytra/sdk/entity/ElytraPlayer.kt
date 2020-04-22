package io.elytra.sdk.entity

import com.flowpowered.network.Message
import com.mojang.authlib.GameProfile
import io.elytra.api.chat.ChatMode
import io.elytra.api.chat.TextComponent
import io.elytra.api.entity.player.Player
import io.elytra.api.entity.player.PlayerReliability
import io.elytra.api.enum.Effect
import io.elytra.api.events.EventBus
import io.elytra.api.io.i18n.MessageBuilder
import io.elytra.api.world.Position
import io.elytra.api.world.World
import io.elytra.api.world.enums.GameMode
import io.elytra.api.world.enums.WorldType
import io.elytra.sdk.events.player.PlayerJoinEvent
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.message.play.inbound.PlayerAbilitiesMessage
import io.elytra.sdk.network.protocol.message.play.outbound.*
import io.elytra.sdk.server.Elytra
import io.elytra.sdk.world.ElytraChunk
import kotlinx.coroutines.*

data class ElytraPlayer(
    var id: Int,
    var sessionId: String,

    override var displayName: String,
    override var gameProfile: GameProfile,
    override var reliability: PlayerReliability,
    override var online: Boolean,
    override var banned: Boolean,
    override var position: Position,
    override var world: World
) : ElytraHumanoid(world = world, position = position), Player {

    override var gamemode: GameMode = GameMode.CREATIVE
        set(value) {
            field = value
            sendPacket(ChangeGameStateMessage(ChangeGameStateMessage.Reason.GAMEMODE, value.value.toFloat()))
        }

    override var flying: Boolean = false
        set(value) {
            field = value

            if (!flying) {
                field = false
            }

            updatePlayerAbilities()
        }

    override var walkSpeed: Float = 0.2f
        set(value) {
            field = value
            updatePlayerAbilities()
        }

    override var flySpeed: Float = 0.1f
        set(value) {
            field = value
            updatePlayerAbilities()
        }

    override fun join() {
        val spawn = Elytra.server.mainWorld.spawnPoint

        val joinMessage = JoinGameMessage(
            id,
            gamemode,
            0,
            0,
            Elytra.server.serverDescriptor.options.maxPlayers,
            WorldType.FLAT.prettyName,
            32,
            false,
            false)

        sendPacket(joinMessage)
        updatePlayerAbilities()

        online = true
        processChunks(spawn)
    }

    override fun kick(reason: String) {
        session()?.send(DisconnectMessage(reason))
    }

    override fun sendMessage(message: String) {
        sendPacket(OutboundChatMessage(TextComponent(message), ChatMode.FEEDBACK))
    }

    override fun sendMessage(vararg messages: String) {
        val builder = StringBuilder()

        for (arg in messages) {
            builder.append(" ").append(arg)
        }

        sendMessage(builder.toString())
    }

    override fun playEffect(position: Position, effect: Effect, metadata: Int) {
        sendPacket(PlayEffectMessage(effect.id, position, metadata))
    }

    override fun sendPacket(packet: Message) {
        session()?.sendWithFuture(packet)
    }

    override fun tick() {
        super.tick()
    }

    private fun processChunks(spawn: Position) {
        val player = this

        GlobalScope.launch(Dispatchers.Default) {
            var i = 0
            for (x in -1 until ((spawn.x * 2) / 16 + 1).toInt()) {
                for (z in -1 until ((spawn.z * 2) / 16 + 1).toInt()) {
                    val chunk = Elytra.server.mainWorld.getChunkAt(x, z)
                    sendPacket(ChunkDataMessage(x, z, chunk as ElytraChunk))
                    i++

                    if (i == 100) {
                        // Load 100 chunks during the "Loading terrain" screen
                        // and only then let the player join, to prevent client side lag
                        withContext(Dispatchers.Default) {
                            sendPacket(PlayerPositionAndLookMessage(spawn))
                            EventBus.post(PlayerJoinEvent(player))

                            MessageBuilder("console.player.joined").with(
                                "player" to displayName,
                                "uuid" to gameProfile.id
                            ).getOrBuild().also(Elytra.console::info)
                        }
                    }
                    if (i > 100) {
                        // After the player joined, keep sending the missing chunks with 50ms delay
                        delay(50)
                    }
                }
            }
        }
    }

    private fun session(): NetworkSession? {
        return Elytra.server.sessionRegistry.get(sessionId)
    }

    private fun updatePlayerAbilities() {
        val isCreative = gamemode == GameMode.CREATIVE

        val flags = ((if (isCreative) 8 else 0)
            or (if (flying) 4 else 0)
            or (if (flying) 2 else 0)
            or (if (isCreative) 1 else 0))

        sendPacket(PlayerAbilitiesMessage(flags, flySpeed / 2F, walkSpeed / 2F))
    }
}

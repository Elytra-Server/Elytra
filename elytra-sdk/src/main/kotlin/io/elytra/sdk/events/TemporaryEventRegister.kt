package io.elytra.sdk.events

import com.google.common.collect.ImmutableList
import io.elytra.api.chat.ChatColor
import io.elytra.api.chat.ChatMode
import io.elytra.api.chat.TextComponent
import io.elytra.api.events.EventBus
import io.elytra.api.events.Registrable
import io.elytra.api.events.listen
import io.elytra.sdk.entity.ElytraPlayer
import io.elytra.sdk.events.player.PlayerJoinEvent
import io.elytra.sdk.network.protocol.message.play.outbound.*
import io.elytra.sdk.server.Elytra

@Deprecated("Only used for development testing")
class TemporaryEventRegister : Registrable {

    override fun register() {
        EventBus.listen<PlayerJoinEvent>()
            .subscribe {
                val player = it.player
                val onlinePlayers = Elytra.players()

                val joinMessage = TextComponent("${ChatColor.YELLOW}${player.displayName} joined the game")
                val playerListData = AddPlayerData(
                    0,
                    player.gamemode,
                    player.gameProfile, TextComponent(player.displayName)
                )

                Elytra.sendPacketToAll(OutboundChatMessage(joinMessage, ChatMode.PLAYER))
                Elytra.sendPacketToAll(PlayerListItemMessage(Action.ADD_PLAYER, ImmutableList.of(playerListData)))

                onlinePlayers.forEach { onlinePlayer ->
                    val onlinePlayerListData = AddPlayerData(
                        0,
                        onlinePlayer.gamemode,
                        onlinePlayer.gameProfile, TextComponent(onlinePlayer.displayName)
                    )
                    player.sendPacket(PlayerListItemMessage(Action.ADD_PLAYER, ImmutableList.of(onlinePlayerListData)))

                    if ((onlinePlayer as ElytraPlayer).id != (player as ElytraPlayer).id) {
                        onlinePlayer.sendMessage("Send spawm")
                        val spawnPlayer = SpawnPlayerMessage(
                            player.id,
                            player.gameProfile.id,
                            player.position.x,
                            player.position.y,
                            player.position.z,
                            player.position.pitch,
                            player.position.yaw)
                        onlinePlayer.sendPacket(spawnPlayer)

                        val spawnPlayerMe = SpawnPlayerMessage(
                            onlinePlayer.id,
                            onlinePlayer.gameProfile.id,
                            onlinePlayer.position.x,
                            onlinePlayer.position.y,
                            onlinePlayer.position.z,
                            onlinePlayer.position.pitch,
                            onlinePlayer.position.yaw)
                        player.sendPacket(spawnPlayerMe)
                        player.sendMessage("Send spawm")
                    }
                }

                player.sendPacket(PlayerPositionAndLookMessage(player.position))
                player.online = true
            }
    }
}

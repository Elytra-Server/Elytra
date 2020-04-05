package io.elytra.sdk.events

import com.google.common.collect.ImmutableList
import io.elytra.api.chat.ChatMode
import io.elytra.api.chat.Colors
import io.elytra.api.chat.TextComponent
import io.elytra.api.events.EventBus
import io.elytra.api.events.Registrable
import io.elytra.api.events.listen
import io.elytra.sdk.network.protocol.message.play.Action
import io.elytra.sdk.network.protocol.message.play.AddPlayerData
import io.elytra.sdk.network.protocol.message.play.OutboundChatMessage
import io.elytra.sdk.network.protocol.message.play.PlayerListItemMessage
import io.elytra.sdk.server.Elytra

@Deprecated("Only used for development testing")
class TemporaryEventRegister : Registrable {

    override fun register() {
        EventBus.listen<PlayerJoinEvent>()
            .subscribe {
                val player = it.player
                val onlinePlayers = Elytra.players()

                val joinMessage = TextComponent("${Colors.YELLOW}${player.displayName} joined the game")
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
                        player.gameProfile, TextComponent(onlinePlayer.displayName)
                    )

                    onlinePlayer.sendPacket(PlayerListItemMessage(Action.ADD_PLAYER, ImmutableList.of(onlinePlayerListData)))
                }

                player.spawn()
            }
    }
}

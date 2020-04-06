package io.elytra.sdk.network.protocol.message.play.outbound

import com.flowpowered.network.Message
import com.mojang.authlib.GameProfile
import io.elytra.api.chat.TextComponent
import io.elytra.api.world.enums.GameMode

enum class Action() {
    ADD_PLAYER,
    UPDATE_GAME_MODE,
    UPDATE_LATENCY,
    UPDATE_DISPLAY_NAME,
    REMOVE_PLAYER
}

data class AddPlayerData(
    val ping: Int,
    val gameMode: GameMode,
    val gameProfile: GameProfile,
    val displayName: TextComponent
)

data class PlayerListItemMessage(
    val action: Action,
    val players: List<AddPlayerData>
) : Message

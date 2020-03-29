package io.elytra.sdk.network.protocol.message.play

import com.flowpowered.network.Message
import com.mojang.authlib.GameProfile
import io.elytra.api.chat.ChatComponent
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
	val displayName: ChatComponent//TODO Put ITextComponent here
	)

data class PlayerListItemMessage(
	val action: Action,
	val players :List<AddPlayerData>
) : Message

package io.elytra.api.entity

import com.mojang.authlib.GameProfile
import io.elytra.api.chat.TextComponent
import io.elytra.api.world.enums.GameMode
import io.elytra.api.world.Position

interface Player {
	var displayName: String
	var gameProfile: GameProfile?

	var mode: PlayerMode
	var online: Boolean
	var banned: Boolean

	var gamemode: GameMode

	fun kick(reason: String)
	fun sendMessage(message: String)
	fun sendMessage(textComponent: TextComponent)
}

enum class PlayerMode {
	PREMIUM,
	OFFLINE
}

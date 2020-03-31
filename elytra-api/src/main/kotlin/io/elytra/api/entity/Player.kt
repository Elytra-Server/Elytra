package io.elytra.api.entity

import com.mojang.authlib.GameProfile
import io.elytra.api.chat.ChatComponent
import io.elytra.api.world.Position
import io.elytra.api.world.enums.GameMode

interface Player {
	var displayName: String
	var gameProfile: GameProfile?

	var mode: PlayerMode
	var online: Boolean
	var banned: Boolean

	var exp: Int
	var expLevel: Int

	var gamemode: GameMode

	fun kick(reason: String)

	fun sendMessage(message: String)

	fun sendMessage(chatComponent: ChatComponent)
}

enum class PlayerMode {
	PREMIUM,
	OFFLINE
}

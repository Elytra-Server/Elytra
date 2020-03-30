package io.elytra.api.entity

import com.flowpowered.network.Message
import com.mojang.authlib.GameProfile
import io.elytra.api.chat.ChatComponent
import io.elytra.api.world.enums.GameMode
import io.elytra.api.world.Position
import java.util.*

interface Player {
	var displayName: String
	var gameProfile: GameProfile?

	var mode: PlayerMode
	var online: Boolean
	var banned: Boolean

	var exp: Int
	var expLevel: Int

	var position: Position
	var gamemode: GameMode

	fun kick(reason: String)

	fun sendMessage(message: String)

	fun sendMessage(chatComponent: ChatComponent)

}

enum class PlayerMode{
	PREMIUM,
	OFFLINE
}

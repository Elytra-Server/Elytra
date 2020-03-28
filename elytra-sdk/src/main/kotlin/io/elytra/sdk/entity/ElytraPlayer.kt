package io.elytra.sdk.entity

import com.mojang.authlib.GameProfile
import io.elytra.api.entity.Player
import io.elytra.api.world.enums.GameMode
import io.elytra.api.world.Position
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.server.Elytra

data class ElytraPlayer(
	var sessionId: String,
	override var displayName: String,
	override var gameProfile: GameProfile?,
	override var premium: Boolean,
	override var online: Boolean,
	override var banned: Boolean,
	override var exp: Int,
	override var expLevel: Int,
	override var position: Position = Position.EMPTY,
	override var gamemode: GameMode = GameMode.SURVIVAL
) : Player {

	fun session(): NetworkSession? {
		return Elytra.server.sessionRegistry.get(sessionId)
	}

}

package io.elytra.sdk.entity

import com.mojang.authlib.GameProfile
import io.elytra.api.entity.Player
import io.elytra.api.world.enums.GameMode
import io.elytra.api.world.Position
import io.elytra.sdk.network.NetworkSession
import io.elytra.api.world.Difficulty
import io.elytra.api.world.GameMode
import io.elytra.api.world.Location
import io.elytra.sdk.network.protocol.message.play.EntityStatusMessage
import io.elytra.sdk.network.protocol.message.play.HeldItemChangeMessage
import io.elytra.sdk.network.protocol.message.play.JoinGameMessage
import io.elytra.sdk.network.protocol.message.play.PlayerPosLookMessage
import io.elytra.sdk.server.Elytra
import java.util.*

data class ElytraPlayer(
	var sessionId: String,
	override var displayName: String,
	override var gameProfile: GameProfile?,
	override var premium: Boolean,
	override var online: Boolean,
	override var banned: Boolean,
	override var exp: Int,
	override var expLevel: Int,
	override var location: Location = Location.EMPTY,
	override var gamemode: GameMode = GameMode.SURVIVAL
) : Player {

	fun session(): NetworkSession? {
		return Elytra.server.sessionRegistry.get(sessionId)
	}

}

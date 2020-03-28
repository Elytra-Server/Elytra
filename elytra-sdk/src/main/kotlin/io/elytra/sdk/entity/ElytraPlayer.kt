package io.elytra.sdk.entity

import com.mojang.authlib.GameProfile
import io.elytra.api.entity.Player
import io.elytra.api.world.enums.GameMode
import io.elytra.api.world.Position
import java.util.*

data class ElytraPlayer(
	var sessionId: String,
	override var username: String,
	override var uuid: UUID,
	override var gameProfile: GameProfile?,
	override var online: Boolean,
	override var banned: Boolean,
	override var exp: Int,
	override var expLevel: Int,
	override var position: Position = Position.EMPTY,
	override var gamemode: GameMode = GameMode.SURVIVAL
) : Player {

	fun session(){

	}

}

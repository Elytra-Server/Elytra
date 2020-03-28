package io.elytra.api.entity

import com.mojang.authlib.GameProfile
import io.elytra.api.world.GameMode
import io.elytra.api.world.Position
import java.util.*

interface Player {

	var username: String
	var uuid: UUID
	var gameProfile: GameProfile?

	var online: Boolean
	var banned: Boolean

	var exp: Int
	var expLevel: Int

	var position: Position
	var gamemode: GameMode

}

package io.inb.api.entity

import com.mojang.authlib.GameProfile
import io.inb.api.world.GameMode
import io.inb.api.world.Location
import java.util.*

interface Player {

	var username: String
	var uuid: UUID
	var gameProfile: GameProfile?

	var online: Boolean
	var banned: Boolean

	var exp: Int
	var expLevel: Int

	var location: Location
	var gamemode: GameMode

}

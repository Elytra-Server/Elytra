package io.inb.api.entity

import com.mojang.authlib.GameProfile
import io.inb.api.network.NetworkSession
import io.inb.api.world.Location
import java.util.*

interface Player {

	var username: String
	var uuid: UUID
	var location: Location
	var gameProfile: GameProfile?

	fun join(session: NetworkSession)

}

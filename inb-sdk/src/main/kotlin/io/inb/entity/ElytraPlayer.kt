package io.inb.entity

import com.mojang.authlib.GameProfile
import io.inb.api.entity.Player
import io.inb.network.NetworkSession
import io.inb.network.protocol.message.play.*
import io.inb.api.world.Difficulty
import io.inb.api.world.GameMode
import io.inb.api.world.Location
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
	override var location: Location = Location.EMPTY,
	override var gamemode: GameMode = GameMode.SURVIVAL
) : Player {

	fun join(session: NetworkSession) {
		val joinMessage = JoinGameMessage(1, GameMode.CREATIVE, 0, Difficulty.NORMAL, 1, "flat", false)
		val positionMessage = PlayerPosLookMessage(150.0, 150.0, 150.0, 50f, 50f, 0x01, 1)

		session.send(joinMessage)
		session.send(HeldItemChangeMessage(1))
		session.send(EntityStatusMessage(1, 24))
		session.send(positionMessage)
	}

	fun session(){

	}

}

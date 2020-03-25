package io.inb.api.entity

import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.message.play.JoinGameMessage
import io.inb.api.network.protocol.message.play.PlayerPositionAndLookMessage
import io.inb.api.network.protocol.message.play.PositionLookMessage
import io.inb.api.world.Location
import java.util.*

data class Player(var username: String, var uuid: UUID, var location: Location = Location.EMPTY){

	fun join(session: NetworkSession){
		val joinMessage = JoinGameMessage(0,0,0,0,1,"flat",false)
		val positionMessage = PositionLookMessage(location)

		session.send(joinMessage)

		//TODO: Spawn packet

		//session.send(positionMessage)
	}
}

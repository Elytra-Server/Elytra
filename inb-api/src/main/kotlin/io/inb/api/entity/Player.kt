package io.inb.api.entity

import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.message.play.*
import io.inb.api.world.Location
import java.util.*

data class Player(var username: String, var uuid: UUID, var location: Location = Location.EMPTY){

	fun join(session: NetworkSession){
		val joinMessage = JoinGameMessage(1,GameMode.CREATIVE.value,0,0,1,"flat",false)
		val positionMessage = PlayerPosLookMessage(150.0,150.0,150.0,50f,50f,0x01,1)

		session.send(joinMessage)
		session.send(HeldItemChangeMessage(1))
		session.send(EntityStatusMessage(1,24))
		session.send(positionMessage)
		//TODO: Spawn packet
	}
}

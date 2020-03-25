package io.inb.api.entity

import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.message.play.JoinGameMessage
import java.util.*

data class Player(var username: String, var uuid: UUID){

	fun join(session: NetworkSession){
		println("Entrou no join")
		val message = JoinGameMessage(0,0,0,0,1,"flat",false)

		session.send(message)
	}
}

package io.inb.api.entity

import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.message.play.JoinGameMessage

data class Player(val session: NetworkSession, val username: String){

	fun join(){
		session.send(JoinGameMessage(0,
			0,
			0,
			0,
			10,
			"flat",
			50,
			reducedDebugInfo = true,
			enableRespawnScreen = true
		))
	}
}

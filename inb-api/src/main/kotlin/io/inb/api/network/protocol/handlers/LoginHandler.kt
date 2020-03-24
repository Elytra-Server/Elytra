package io.inb.api.network.protocol.handlers

import io.inb.api.entity.Player
import io.inb.api.network.Session
import io.inb.api.network.State
import io.inb.api.network.protocol.message.LoginStartMessage

class LoginHandler : PacketHandler<LoginStartMessage>{

	override fun handle(session: Session, body: LoginStartMessage) {
		if(session.state != State.LOGIN){
			session.disconnect("Already logged in!")
			return
		}

		session.state = State.PLAYING
		session.sendPacket(LoginStartMessage(body.username)) //TODO: Send real values

		val player = Player(session, body.username)
		session.player = player
	}
}

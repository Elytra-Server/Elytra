package io.inb.api.protocol.handlers

import io.inb.api.entity.Player
import io.inb.api.network.Session
import io.inb.api.network.State
import io.inb.api.protocol.PacketHandler
import io.inb.api.protocol.packets.LoginPacket

class LoginHandler : PacketHandler<LoginPacket>{

	override fun handle(session: Session, body: LoginPacket) {
		if(session.state != State.LOGIN){
			TODO("Disconnect, already logged in")
			return
		}

		session.state = State.PLAYING
		session.sendPacket(LoginPacket("", 0)) //TODO: Send real values

		val player = Player(session, body.username)
		session.player = player
	}
}

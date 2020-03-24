package io.inb.api.network

import io.inb.api.utils.Tickable
import io.netty.channel.Channel


object SessionManager : Tickable {
	private val sessions: MutableMap<Channel, Session> = mutableMapOf()

	fun getSession(channel: Channel): Session? {
		return sessions[channel]
	}

	fun openSession(session: Session) {
		sessions[session.channel] = session
	}

	fun closeSession(channel: Channel?) {
		sessions.remove(channel)
	}

	fun activeSessions(): Int {
		return sessions.size
	}

	override fun tick() {
		for((_, session) in sessions){
			session.tick()
		}
	}

}

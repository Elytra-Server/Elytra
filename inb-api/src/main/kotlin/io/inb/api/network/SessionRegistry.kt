package io.inb.api.network

import io.inb.api.utils.Tickable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class SessionRegistry(
	private val sessions: ConcurrentMap<Session, Boolean> = ConcurrentHashMap()
) : Tickable {

	fun add(session: Session) {
		sessions[session] = true
	}

	fun closeSession(session: Session) {
		sessions.remove(session)
	}

	fun activeSessions(): Int {
		return sessions.size
	}

	override fun tick() {
		sessions.keys.forEach { it.tick() }
	}

}

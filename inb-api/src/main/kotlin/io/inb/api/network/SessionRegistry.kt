package io.inb.api.network

import io.inb.api.utils.Tickable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * Manages the connection sessions
 *
 * @see [io.inb.api.network.NetworkSession]
 */
class SessionRegistry(
	private val sessions: ConcurrentMap<NetworkSession, Boolean> = ConcurrentHashMap()
) : Tickable {

	fun add(networkSession: NetworkSession) {
		sessions[networkSession] = true
	}

	fun remove(networkSession: NetworkSession) {
		sessions.remove(networkSession)
	}

	fun get(id: String): NetworkSession? {
		return sessions.keys.first { session -> session.sessionId == id };
	}

	fun activeSessions(): Int {
		return sessions.size
	}

	fun clearSessions() = sessions.clear()

	override fun tick() {
		sessions.keys.forEach {
			it.tick()
		}
	}
}

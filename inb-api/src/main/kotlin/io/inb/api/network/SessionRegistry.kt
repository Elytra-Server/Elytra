package io.inb.api.network

import io.inb.api.utils.Tickable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class SessionRegistry(
	private val sessions: ConcurrentMap<NetworkSession, Boolean> = ConcurrentHashMap()
) : Tickable {

	fun add(networkSession: NetworkSession) {
		sessions[networkSession] = true
	}

	fun remove(networkSession: NetworkSession) {
		sessions.remove(networkSession)
	}

	fun activeSessions(): Int {
		return sessions.size
	}

	override fun tick() {
		sessions.keys.forEach {
			it.tick()
		}
	}
}

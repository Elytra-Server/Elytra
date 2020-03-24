package io.inb.api.network

import io.inb.api.utils.Tickable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class SessionRegistry(
	private val sessions: ConcurrentMap<InbSession, Boolean> = ConcurrentHashMap()
) : Tickable {

	fun add(inbSession: InbSession) {
		sessions[inbSession] = true
	}

	fun remove(inbSession: InbSession) {
		sessions.remove(inbSession)
	}

	fun activeSessions(): Int {
		return sessions.size
	}

	override fun tick() {
		sessions.keys.forEach { it.tick() }
	}
}

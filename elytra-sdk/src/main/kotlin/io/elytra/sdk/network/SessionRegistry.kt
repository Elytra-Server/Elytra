package io.elytra.sdk.network

import io.elytra.api.registry.Registry
import io.elytra.api.utils.Tickable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.stream.Stream

/**
 * Manages the connection sessions
 *
 * @see [io.inb.network.NetworkSession]
 */
class SessionRegistry(
	private val sessions: ConcurrentMap<NetworkSession, Boolean> = ConcurrentHashMap()
) : Registry<NetworkSession,String>,Tickable {

	override fun add(record: NetworkSession) {
		sessions[record] = true
	}

	override fun remove(record: NetworkSession) {
		sessions.remove(record)
	}

	override fun get(record: String): NetworkSession? {
		return sessions.keys.first { session -> session.sessionId == record };
	}

	override fun size(): Int {
		return sessions.size
	}

	override fun clear() = sessions.clear()

	override fun tick() {
		sessions.keys.forEach {
			it.tick()
		}
	}

	override fun stream(): Stream<NetworkSession> {
		return sessions.keys.stream()
	}
}

package io.elytra.sdk.network

import io.elytra.api.registry.Registry
import io.elytra.api.utils.Tickable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.stream.Stream

/**
 * Manages the connection sessions
 *
 * @see [io.elytra.sdk.network.NetworkSession]
 */
class SessionRegistry(
	private var sessions: ConcurrentMap<NetworkSession, Boolean> = ConcurrentHashMap()
) : Registry<NetworkSession,String>,Tickable {

	override fun add(target: NetworkSession) {
		sessions[target] = true
	}

	override fun remove(target: NetworkSession) {
		sessions.remove(target)
	}

	override fun get(target: String): NetworkSession? {
		return sessions.keys.first { session -> session.sessionId == target };
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

	override fun iterator(): Iterator<NetworkSession> {
		return sessions.keys.iterator()
	}
}

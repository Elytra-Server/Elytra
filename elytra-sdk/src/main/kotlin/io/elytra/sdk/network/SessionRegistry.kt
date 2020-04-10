package io.elytra.sdk.network

import io.elytra.api.registry.Registry
import io.elytra.api.utils.Tickable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Manages the connection sessions
 *
 * @see [io.elytra.sdk.network.NetworkSession]
 */
class SessionRegistry : Registry<String, NetworkSession>, Tickable {

    private val sessions: ConcurrentMap<NetworkSession, Boolean> = ConcurrentHashMap()
    private val mutex = Mutex()

    override suspend fun add(target: NetworkSession) = mutex.withLock {
        sessions[target] = true
    }

    override suspend fun remove(target: NetworkSession): Unit = mutex.withLock {
        sessions.remove(target)
    }

    override fun get(key: String): NetworkSession? {
        return sessions.keys.first { session -> session.sessionId == key }
    }

    override fun has(key: String): Boolean {
        return sessions.keys.any { session -> session.sessionId == key }
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

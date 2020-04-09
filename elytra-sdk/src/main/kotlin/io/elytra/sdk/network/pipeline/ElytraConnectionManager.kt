package io.elytra.sdk.network.pipeline

import com.flowpowered.network.ConnectionManager
import com.flowpowered.network.session.Session
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.SessionRegistry
import io.netty.channel.Channel
import java.util.concurrent.atomic.AtomicReference
import kotlinx.coroutines.runBlocking

/**
 * Manages the connections within the netty channels
 */
class ElytraConnectionManager(
    private val sessionRegistry: SessionRegistry
) : ConnectionManager {

    override fun sessionInactivated(session: Session) {
        runBlocking {
            sessionRegistry.remove(session as NetworkSession)
        }
    }

    override fun newSession(c: Channel): Session {
        val atomicReference = AtomicReference<NetworkSession>()

        runBlocking {
            val newSession = NetworkSession(c)
            atomicReference.set(newSession)

            sessionRegistry.add(atomicReference.acquire)
        }

        println("got new session ${atomicReference.acquire}")
        return atomicReference.acquire
    }

    override fun shutdown() {
        sessionRegistry.clear()
    }
}

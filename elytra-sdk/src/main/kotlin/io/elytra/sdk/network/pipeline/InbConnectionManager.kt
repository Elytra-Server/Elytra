package io.elytra.sdk.network.pipeline

import com.flowpowered.network.ConnectionManager
import com.flowpowered.network.session.Session
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.SessionRegistry
import io.netty.channel.Channel

/**
 * Manages the connections within the netty channels
 */
class InbConnectionManager(
	private val sessionRegistry: SessionRegistry
) : ConnectionManager {

	override fun sessionInactivated(session: Session) {
		sessionRegistry.remove(session as NetworkSession)
	}

	override fun newSession(c: Channel): Session {
		val session = NetworkSession(c)
		sessionRegistry.add(session)
		return session
	}

	override fun shutdown() {
		sessionRegistry.clear()
	}

}

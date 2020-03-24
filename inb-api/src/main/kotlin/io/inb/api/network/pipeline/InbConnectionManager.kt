package io.inb.api.network.pipeline

import com.flowpowered.network.ConnectionManager
import com.flowpowered.network.session.Session
import io.inb.api.network.InbSession
import io.inb.api.network.SessionRegistry
import io.netty.channel.Channel

class InbConnectionManager(
	private val sessionRegistry: SessionRegistry = SessionRegistry()
) : ConnectionManager {


	override fun sessionInactivated(session: Session) {
		sessionRegistry.remove(session as InbSession)
	}

	override fun newSession(c: Channel): Session {
		val session = InbSession(c)
		sessionRegistry.add(session)

		return session
	}

	override fun shutdown() {
		TODO("not implemented")
	}

}

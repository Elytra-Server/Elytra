package io.inb.api.network.pipeline

import com.flowpowered.network.ConnectionManager
import com.flowpowered.network.session.Session
import io.inb.api.InbServer
import io.inb.api.network.NetworkSession
import io.inb.api.network.SessionRegistry
import io.inb.api.network.protocol.packets.PlayPacket
import io.netty.channel.Channel

class InbConnectionManager(
	private val sessionRegistry: SessionRegistry = InbServer.getServer().sessionRegistry
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
		TODO("not implemented")
	}

}

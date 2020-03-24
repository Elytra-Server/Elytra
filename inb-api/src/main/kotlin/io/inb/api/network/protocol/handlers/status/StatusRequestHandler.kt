package io.inb.api.network.protocol.handlers.status

import com.flowpowered.network.MessageHandler
import io.inb.api.events.ServerListPingEvent
import io.inb.api.io.EventBus
import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.message.status.StatusRequestMessage

/**
 * @see https://wiki.vg/Server_List_Ping
 */
class StatusRequestHandler : MessageHandler<NetworkSession, StatusRequestMessage> {

	override fun handle(session: NetworkSession, message: StatusRequestMessage) {
		val event = ServerListPingEvent(
			"§aInb - Test Server",
			"",
			480,
			0
		)

		EventBus.post(event)

		println("StatusRequestHandler - $message")
	}
}

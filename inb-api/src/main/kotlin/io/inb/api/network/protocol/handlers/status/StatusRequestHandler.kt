package io.inb.api.network.protocol.handlers.status

import com.google.gson.Gson
import io.inb.api.events.ServerListPingEvent
import io.inb.api.io.EventBus
import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.handlers.InbMessageHandler
import io.inb.api.network.protocol.message.status.StatusRequestMessage
import io.inb.api.network.protocol.message.status.StatusResponseMessage
import io.inb.api.server.InbServer
import io.inb.api.server.Server
import kotlin.collections.ArrayList

/**
 * @see https://wiki.vg/Server_List_Ping
 */
class StatusRequestHandler : InbMessageHandler<StatusRequestMessage>() {

	override fun handle(session: NetworkSession, message: StatusRequestMessage) {
		session.server = InbServer.getServer()
		val motd = session.server?.motd ?: return

		val json: String = Gson().toJson(
			StatusResponse(
				Version(
					motd.pingText,
					InbServer.PROTOCOL_VERSION
				),
				Players(
					motd.maxPlayers,
					0,
					ArrayList()
				),
				Description(motd.description)
			)
		)

		EventBus.post(ServerListPingEvent(motd))
		session.send(StatusResponseMessage(json))
		session.channel.close()
	}

	private data class StatusResponse(
		val version: Version,
		val players: Players,
		val description: Description
	)

	private data class Description(val text: String)

	private data class Players(
		val max: Int,
		val online: Int,
		val sample: List<Player>
	)

	private data class Player(val name: String, val id: String)

	private data class Version(val name: String, val protocol: Int)

}

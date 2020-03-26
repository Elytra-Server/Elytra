package io.inb.api.network.protocol.handlers.status

import com.google.gson.Gson
import io.inb.api.network.NetworkSession
import io.inb.api.network.protocol.handlers.InbMessageHandler
import io.inb.api.network.protocol.message.status.ServerQueryMessage
import io.inb.api.network.protocol.message.status.ServerInfoMessage
import io.inb.api.server.InbServer
import kotlin.collections.ArrayList

/**
 * @see https://wiki.vg/Server_List_Ping
 */
class ServerQueryHandler : InbMessageHandler<ServerQueryMessage>() {

	override fun handle(session: NetworkSession, message: ServerQueryMessage) {
		session.server = InbServer.getServer()

		val serverDescriptor = session.server?.serverDescriptor ?: return
		val motd = serverDescriptor.motd

		val json: String = Gson().toJson(
			StatusResponse(
				Version(
					motd.pingText,
					InbServer.PROTOCOL_VERSION
				),
				Players(
					serverDescriptor.options.maxPlayers,
					session.server!!.sessionRegistry.activeSessions(), //FIXME: Get from a player registry
					ArrayList()
				),
				Description(motd.description)
			)
		)

		serverDescriptor.motd = motd

		println(session.server?.serverDescriptor)
		session.send(ServerInfoMessage(json))
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

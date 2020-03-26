package io.elytra.sdk.network.protocol.handlers.status

import com.google.gson.Gson
import io.elytra.sdk.Elytra
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.handlers.InbMessageHandler
import io.elytra.sdk.network.protocol.message.status.ServerQueryMessage
import io.elytra.sdk.network.protocol.message.status.ServerInfoMessage
import io.elytra.sdk.network.protocol.ProtocolInfo
import io.elytra.sdk.server.Elytra
import kotlin.collections.ArrayList

/**
 * @see https://wiki.vg/Server_List_Ping
 */
class ServerQueryHandler : InbMessageHandler<ServerQueryMessage>() {

	override fun handle(session: NetworkSession, message: ServerQueryMessage) {
		session.server = Elytra.server

		val serverDescriptor = session.server?.serverDescriptor ?: return
		val motd = serverDescriptor.motd

		val json: String = Gson().toJson(
                StatusResponse(
                        Version(
                                motd.pingText,
                                ProtocolInfo.CURRENT_PROTOCOL
                        ),
                        Players(
                                serverDescriptor.options.maxPlayers,
                                0, //FIXME: Get from a player registry
                                ArrayList()
                        ),
                        Description(motd.description)
                )
		)

		serverDescriptor.motd = motd
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

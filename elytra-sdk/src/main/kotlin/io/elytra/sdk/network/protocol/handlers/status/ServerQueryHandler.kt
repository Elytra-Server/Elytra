package io.elytra.sdk.network.protocol.handlers.status

import com.google.gson.Gson
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.ProtocolInfo
import io.elytra.sdk.network.protocol.handlers.ElytraMessageHandler
import io.elytra.sdk.network.protocol.message.status.ServerInfoMessage
import io.elytra.sdk.network.protocol.message.status.ServerQueryMessage
import io.elytra.sdk.server.Elytra

/**
 * @see https://wiki.vg/Server_List_Ping
 */
class ServerQueryHandler : ElytraMessageHandler<ServerQueryMessage>() {

    override fun handle(session: NetworkSession, message: ServerQueryMessage) {
        val serverDescriptor = Elytra.server.serverDescriptor

        val json: String = Gson().toJson(
                StatusResponse(
                        Version(
                                serverDescriptor.motd.pingText,
                                ProtocolInfo.CURRENT_PROTOCOL
                        ),
                        Players(
                                serverDescriptor.options.maxPlayers,
                                Elytra.server.playerRegistry.size(),
                                ArrayList()
                        ),
                        Description(serverDescriptor.motd.description)
                )
        )

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

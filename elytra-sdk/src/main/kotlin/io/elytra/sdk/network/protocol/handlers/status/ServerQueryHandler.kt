package io.elytra.sdk.network.protocol.handlers.status

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

        val players = StringBuilder().run {
            Elytra.players().forEach {
                append("{\"name\":\"")
                append(it.displayName)
                append("\",\"id\":\"")
                append(it.gameProfile.id.toString())
                append("\"},")
            }

            if (!isEmpty() && get(length - 1) == ',') {
                return@run substring(0, length - 1)
            }

            return@run toString()
        }

        val json = """
{"version": {
    "name": "${ProtocolInfo.MINECRAFT_VERSION}",
    "protocol": ${ProtocolInfo.CURRENT_PROTOCOL}
},"players": {
    "max": ${serverDescriptor.options.maxPlayers},
    "online": ${Elytra.server.playerRegistry.size()},
    "sample": [$players]
},"description": {"text": "${serverDescriptor.motd.description}"}}"""

        session.send(ServerInfoMessage(json))
        Elytra.console.info("Server query received from ${session.address.address.hostAddress}!")
    }
}

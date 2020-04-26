package io.elytra.sdk.server

import com.flowpowered.network.Message
import io.elytra.api.entity.player.Player
import io.elytra.api.server.Server
import io.elytra.api.utils.get

interface ElytraServer : Server {
    companion object : ElytraServer by get()

    fun broadcastPacket(message: Message, filter: (player: Player) -> Boolean = { true })
}

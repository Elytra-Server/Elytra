package io.elytra.sdk.command.defaults

import io.elytra.api.command.CommandIssuer
import io.elytra.api.command.ElytraCommand
import io.elytra.api.command.annotations.CommandSpec
import io.elytra.api.command.argument.ArgumentList
import io.elytra.api.entity.player.Player
import io.elytra.sdk.entity.ElytraPlayer
import io.elytra.sdk.network.protocol.message.play.outbound.SpawnPlayerMessage
import io.elytra.sdk.server.ElytraServer

@CommandSpec("debug")
class DebugCommand : ElytraCommand() {

    override fun execute(issuer: CommandIssuer, arguments: ArgumentList) {
        val player = issuer as ElytraPlayer

        ElytraServer.onlinePlayers.forEach { onlinePlayers: Player ->
            if ((onlinePlayers as ElytraPlayer).id != player.id) {
                player.sendPacket(SpawnPlayerMessage(
                    onlinePlayers.id,
                    onlinePlayers.gameProfile.id,
                    onlinePlayers.position.x,
                    onlinePlayers.position.y,
                    onlinePlayers.position.z,
                    onlinePlayers.position.pitch,
                    onlinePlayers.position.yaw))
            }
        }
    }
}

package io.elytra.sdk.commands

import io.elytra.api.command.CommandInfo
import io.elytra.api.command.CommandSender
import io.elytra.api.command.ElytraCommand
import io.elytra.api.command.argument.ArgumentList
import io.elytra.api.command.argument.ArgumentTypes
import io.elytra.api.entity.Player
import io.elytra.sdk.entity.ElytraPlayer
import io.elytra.sdk.network.protocol.message.play.outbound.SpawnPlayerMessage
import io.elytra.sdk.server.Elytra

@CommandInfo("debug")
class DebugCommand : ElytraCommand() {

    init {
        addArgument("spawn", ArgumentTypes.STRING)
    }

    override fun execute(sender: CommandSender, arguments: ArgumentList) {
        val player = sender as ElytraPlayer
        Elytra.players().forEach { onlinePlayers: Player ->
            if ((onlinePlayers as ElytraPlayer).id != player.id) {
                player.sendMessage("KABUM! KRL")
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
        player.sendMessage("KABUM!")
    }
}
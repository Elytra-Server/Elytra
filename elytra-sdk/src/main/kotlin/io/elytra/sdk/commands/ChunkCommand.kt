package io.elytra.sdk.commands

import io.elytra.api.command.CommandIssuer
import io.elytra.api.command.ElytraCommand
import io.elytra.api.command.annotations.CommandSpec
import io.elytra.api.command.argument.ArgumentList
import io.elytra.api.entity.Player

@CommandSpec(label = "chunk")
class ChunkCommand : ElytraCommand() {

    override fun execute(issuer: CommandIssuer, arguments: ArgumentList) {
        val player = issuer as Player

        if (player.world != null) {
            val chunk = player.world.getChunkAt(player.position)
            player.sendMessage("chunk at: ${chunk?.x} - ${chunk?.z}")
        }
    }
}
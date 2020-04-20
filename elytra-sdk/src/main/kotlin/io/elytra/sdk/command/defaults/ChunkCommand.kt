package io.elytra.sdk.command.defaults

import io.elytra.api.command.CommandIssuer
import io.elytra.api.command.ElytraCommand
import io.elytra.api.command.annotations.CommandSpec
import io.elytra.api.command.argument.ArgumentList
import io.elytra.api.entity.Player

@CommandSpec(label = "chunk")
class ChunkCommand : ElytraCommand() {

    override fun execute(issuer: CommandIssuer, arguments: ArgumentList) {
        val player = issuer as Player

        val chunk = player.world.getChunkAt(player.position)
        player.sendMessage("chunk at: ${chunk?.x} - ${chunk?.z}")
    }
}

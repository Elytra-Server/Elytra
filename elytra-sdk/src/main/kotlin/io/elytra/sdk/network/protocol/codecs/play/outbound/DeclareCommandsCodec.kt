package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.DeclareCommandsMessage
import io.netty.buffer.ByteBuf

class DeclareCommandsCodec : OutboundCodec<DeclareCommandsMessage>() {
    override fun encode(buffer: ByteBuf, message: DeclareCommandsMessage): ByteBuf {
        val commands = message.commands.toTypedArray()
        val commandsSize = commands
            // Each alias count as a command, get the amount of aliases plus 1 for the base command
            .map { it.aliases.size + 1 }
            .sum()
        val fakeArgsIndex = commandsSize + 1

        // Commands array size + 1 for the root node + 1 for the fake args node
        ByteBufUtils.writeVarInt(buffer, commandsSize + 2) // Size

        // region --{ root node }--
        buffer.writeByte(0)
        // Child count for the root node
        ByteBufUtils.writeVarInt(buffer, commandsSize)
        // The children indexes
        for (i in 1..commandsSize) {
            // All commands go after the root, after them go the sub-commands
            ByteBufUtils.writeVarInt(buffer, i)
        }
        // endregion --{ Root Node }--

        // region --{ Commands }--
        for (i in 1..commands.size) {
            // Mark as literal command & executable
            buffer.writeByte(0x01 or 0x04)

            val command = commands[i - 1]
            // Child count, 1 for the fake args
            ByteBufUtils.writeVarInt(buffer, 1)
            ByteBufUtils.writeVarInt(buffer, fakeArgsIndex)
            // Command name
            ByteBufUtils.writeUTF8(buffer, command.label) // Arg name

            for (commandAlias in command.aliases) {
                // Register aliases/redirects for the command
                // Mark as literal command & executable & has redirect
                buffer.writeByte(0x01 or 0x04 or 0x08)
                // Child count, 0 since this is a redirect
                ByteBufUtils.writeVarInt(buffer, 0)
                // Base command index to redirect to
                ByteBufUtils.writeVarInt(buffer, i)
                ByteBufUtils.writeUTF8(buffer, commandAlias) // Arg name
            }
        }
        // endregion --{ Commands }--

        // region --{ Command Args }--
        buffer.writeByte(0x02 or 0x04 or 0x10)
        // Child count, 0 only one arg to catch everything
        ByteBufUtils.writeVarInt(buffer, 0)
        ByteBufUtils.writeUTF8(buffer, "Arguments")
        ByteBufUtils.writeUTF8(buffer, "brigadier:string")
        // Greedy string -> Reads the rest of the content after the cursor. Quotes will not be removed.
        ByteBufUtils.writeVarInt(buffer, 2)
        ByteBufUtils.writeUTF8(buffer, "minecraft:ask_server")

        // Index of the root node, we registered it as the first node, so it's 0
        ByteBufUtils.writeVarInt(buffer, 0)
        // endregion --{ Command Args }--

        return buffer
    }
}

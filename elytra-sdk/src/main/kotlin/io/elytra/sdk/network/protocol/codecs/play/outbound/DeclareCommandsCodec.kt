package io.elytra.sdk.network.protocol.codecs.play.outbound

import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.codecs.OutboundCodec
import io.elytra.sdk.network.protocol.message.play.outbound.DeclareCommandsMessage
import io.netty.buffer.ByteBuf

class DeclareCommandsCodec : OutboundCodec<DeclareCommandsMessage>() {
    override fun encode(buf: ByteBuf, message: DeclareCommandsMessage): ByteBuf {
        val commands = message.commands.toTypedArray()
        val commandsSize = commands
            // Each alias count as a command, get the amount of aliases plus 1 for the base command
            .map { it.aliases.size + 1 }
            .sum()
        val fakeArgsIndex = commandsSize + 1

        // Commands array size + 1 for the root node + 1 for the fake args node
        ByteBufUtils.writeVarInt(buf, commandsSize + 2) // Size

        // region --{ root node }--
        buf.writeByte(0)
        // Child count for the root node
        ByteBufUtils.writeVarInt(buf, commandsSize)
        // The children indexes
        for (i in 1..commandsSize) {
            // All commands go after the root, after them go the sub-commands
            ByteBufUtils.writeVarInt(buf, i)
        }
        // endregion --{ Root Node }--

        // region --{ Commands }--
        for (i in 1..commands.size) {
            // Mark as literal command & executable
            buf.writeByte(0x01 or 0x04)

            val command = commands[i - 1]
            // Child count, 1 for the fake args
            ByteBufUtils.writeVarInt(buf, 1)
            ByteBufUtils.writeVarInt(buf, fakeArgsIndex)
            // Command name
            ByteBufUtils.writeUTF8(buf, command.label) // Arg name

            for (commandAlias in command.aliases) {
                // Register aliases/redirects for the command
                // Mark as literal command & executable & has redirect
                buf.writeByte(0x01 or 0x04 or 0x08)
                // Child count, 0 since this is a redirect
                ByteBufUtils.writeVarInt(buf, 0)
                // Base command index to redirect to
                ByteBufUtils.writeVarInt(buf, i)
                ByteBufUtils.writeUTF8(buf, commandAlias) // Arg name
            }
        }
        // endregion --{ Commands }--

        buf.writeByte(0x02 or 0x04 or 0x10)
        // Child count, 0 only one arg to catch everything
        ByteBufUtils.writeVarInt(buf, 0)
        ByteBufUtils.writeUTF8(buf, "Arguments")
        ByteBufUtils.writeUTF8(buf, "brigadier:string")
        // Greedy string -> Reads the rest of the content after the cursor. Quotes will not be removed.
        ByteBufUtils.writeVarInt(buf, 2)
        ByteBufUtils.writeUTF8(buf, "minecraft:ask_server")

        // Index of the root node, we registered it as the first node, so it's 0
        ByteBufUtils.writeVarInt(buf, 0)

        return buf
    }
}

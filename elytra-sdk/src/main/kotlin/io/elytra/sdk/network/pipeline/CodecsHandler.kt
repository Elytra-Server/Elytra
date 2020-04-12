package io.elytra.sdk.network.pipeline

import com.flowpowered.network.Codec
import com.flowpowered.network.Codec.CodecRegistration
import com.flowpowered.network.Message
import com.flowpowered.network.exception.IllegalOpcodeException
import com.flowpowered.network.util.ByteBufUtils
import io.elytra.sdk.network.protocol.packets.BasicPacket
import io.elytra.sdk.server.Elytra
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.EncoderException
import io.netty.handler.codec.MessageToMessageCodec
import java.io.IOException

class CodecsHandler(private val packet: BasicPacket) : MessageToMessageCodec<ByteBuf, Message>() {

    override fun encode(ctx: ChannelHandlerContext, msg: Message, out: MutableList<Any>) {
        val clazz: Class<out Message> = msg::class.java
        val reg: CodecRegistration = packet.getCodecRegistration(clazz)
            ?: throw EncoderException("Unknown message type: $clazz.")

        val headerBuf = ctx.alloc().buffer(8)
        ByteBufUtils.writeVarInt(headerBuf, reg.opcode)

        var messageBuf = ctx.alloc().buffer()
        messageBuf = reg.getCodec<Message>().encode(messageBuf, msg)

        out.add(Unpooled.wrappedBuffer(headerBuf, messageBuf))
    }

    override fun decode(ctx: ChannelHandlerContext, msg: ByteBuf, out: MutableList<Any>) {
        try {
            val codec: Codec<*> = packet.newReadHeader(msg)
            val decoded = codec.decode(msg)

            if (msg.readableBytes() > 0) {
                Elytra.console.warn("Message is too long (${msg.readableBytes()}) - $decoded")
                Elytra.console.warn("Did the ${codec::class.simpleName} miss any fields?")
            }

            if (decoded != null) {
                out.add(decoded)
            }
        } catch (e: IllegalOpcodeException) {
            Elytra.console.error("Failed to find the Codec: " + e.message)
        } catch (e: IOException) {
            Elytra.console.error("Failed to read the Opcode from the buffer.")
            e.printStackTrace()
        }
    }
}

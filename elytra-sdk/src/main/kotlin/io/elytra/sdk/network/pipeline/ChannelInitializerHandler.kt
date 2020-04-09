package io.elytra.sdk.network.pipeline

import com.flowpowered.network.ConnectionManager
import io.elytra.sdk.network.protocol.packets.HandshakePacket
import io.netty.channel.ChannelException
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.socket.SocketChannel
import io.netty.handler.timeout.ReadTimeoutHandler

class ChannelInitializerHandler(
    private val connectionManager: ConnectionManager
) : ChannelInitializer<SocketChannel>() {

    override fun initChannel(ch: SocketChannel) {
        val codecsHandler = CodecsHandler(HandshakePacket())
        val decoderHandler = FramingHandler()
        val messagesHandler = MessageHandler(connectionManager = connectionManager)

        /**
		 * The time in seconds which are elapsed before a client is disconnected due
		 * to a read timeout.
		 */
        val readTimeout = 20

        try {
            ch.config().setOption(ChannelOption.IP_TOS, 0x18)
        } catch (e: ChannelException) {
            println("Your OS does not support type of service.")
        }

        ch.pipeline()
            .addLast("framing", decoderHandler)
            .addLast("codecs", codecsHandler)
            .addLast("readtimeout", ReadTimeoutHandler(readTimeout))
            .addLast("handler", messagesHandler)
    }
}

package io.elytra.sdk.network.pipeline

import com.flowpowered.network.ConnectionManager
import io.elytra.sdk.network.protocol.packets.HandshakePacket
import io.netty.channel.ChannelException
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.socket.SocketChannel


class ChannelInitializerHandler(
	private val connectionManager : ConnectionManager
) : ChannelInitializer<SocketChannel>() {
	override fun initChannel(ch: SocketChannel) {

		val codecsHandler = CodecsHandler(HandshakePacket())
		val decoderHandler = DecoderHandler()
		val messagesHandler = MessageHandler(connectionManager = connectionManager)

		try {
			ch.config().setOption(ChannelOption.IP_TOS, 0x18)
		} catch (e: ChannelException) {
			println("Your OS does not support type of service.")
		}

		ch.pipeline()
			.addLast("decoder", decoderHandler)
			.addLast("codecs", codecsHandler)
			.addLast("handler", messagesHandler)
	}
}

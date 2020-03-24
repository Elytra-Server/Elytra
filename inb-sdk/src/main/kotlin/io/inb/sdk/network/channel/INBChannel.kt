package io.inb.sdk.network.channel

import io.inb.sdk.network.Decoder
import io.inb.sdk.network.Encoder
import io.inb.sdk.network.handler.ServerHandler
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

class INBChannel : ChannelInitializer<SocketChannel>() {

	override fun initChannel(socketChannel: SocketChannel) {
		socketChannel.pipeline()
			.addLast(Decoder())
			.addLast(Encoder())
			.addLast(ServerHandler())
	}
}

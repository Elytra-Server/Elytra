package io.inb.sdk.network.channel

import io.inb.sdk.network.handler.ServerHandler
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

class INBChannel : ChannelInitializer<SocketChannel>() {

	override fun initChannel(socketChannel: SocketChannel) {
		socketChannel.pipeline().addLast(ServerHandler())
	}
}

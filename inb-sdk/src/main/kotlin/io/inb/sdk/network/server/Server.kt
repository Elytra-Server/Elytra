package io.inb.sdk.network.server

import io.inb.sdk.network.channel.INBChannel
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel

internal class Server(port: Int = 25565) {

	private val bootstrap: ServerBootstrap = ServerBootstrap()

	init {
		bootstrap
			.group(NioEventLoopGroup())
			.channel(NioServerSocketChannel::class.java)
			.localAddress(port)
			.childHandler(INBChannel())
	}

	fun start() {
		val server = bootstrap.bind().sync()

		server.channel().closeFuture().sync()
	}
}

package io.inb.sdk.network.server

import io.inb.sdk.network.channel.INBChannel
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel


internal class Server(private val port: Int = 25565) {

	private val bootstrap: ServerBootstrap = ServerBootstrap()


	fun start() {
		val masterGroup: EventLoopGroup = NioEventLoopGroup()
		val workerGroup: EventLoopGroup = NioEventLoopGroup()

		bootstrap
			.group(masterGroup, workerGroup)
			.channel(NioServerSocketChannel::class.java)
			.childHandler(INBChannel())

		val server = bootstrap.bind(port).sync()

		if(server.isSuccess){
			println("INB is ready for connections.")
		}

		server.channel().closeFuture().sync()
		masterGroup.shutdownGracefully()
		workerGroup.shutdownGracefully()
	}
}

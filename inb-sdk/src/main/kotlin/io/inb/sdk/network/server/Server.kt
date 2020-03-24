package io.inb.sdk.network.server

import io.inb.sdk.network.channel.INBChannel
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
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
			.option(ChannelOption.SO_KEEPALIVE, true)
			.childOption(ChannelOption.TCP_NODELAY, true)
			.childHandler(INBChannel())

		val server = bootstrap.bind(port).sync()

		if(server.isSuccess){
			println("INB is ready for connections.")
		}

		server.channel().close()
		masterGroup.shutdownGracefully()
		workerGroup.shutdownGracefully()

		try {
			bootstrap.group().terminationFuture().sync()
			bootstrap.childGroup().terminationFuture().sync()
		} catch (e: InterruptedException) {
			println("Socket server shutdown process interrupted!")
		}
	}
}

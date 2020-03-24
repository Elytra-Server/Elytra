package io.inb.sdk.network.server

import com.flowpowered.network.ConnectionManager
import io.inb.api.network.InbConnectionManager
import io.inb.api.network.pipeline.ChannelInitializerHandler
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

		val connectionManager: ConnectionManager = InbConnectionManager()

		bootstrap
			.group(masterGroup, workerGroup)
			.channel(NioServerSocketChannel::class.java)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.childOption(ChannelOption.TCP_NODELAY, true)
			.childHandler(ChannelInitializerHandler(connectionManager))

		val server = bootstrap.bind(port).sync()

		if (server.isSuccess) {
			println("INB is ready for connections.")
		}

		server.channel().closeFuture().sync()
		masterGroup.shutdownGracefully()
		workerGroup.shutdownGracefully()
	}
}

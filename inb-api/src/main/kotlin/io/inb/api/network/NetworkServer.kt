package io.inb.api.network

import com.flowpowered.network.ConnectionManager
import io.inb.api.network.pipeline.ChannelInitializerHandler
import io.inb.api.network.pipeline.InbConnectionManager
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.epoll.EpollServerSocketChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel

internal class NetworkServer(private val port: Int = 25565) {

	private val bootstrap: ServerBootstrap = ServerBootstrap()


	fun start() {
		val masterGroup: EventLoopGroup = Channels.pickBestEventLoopGroup()
		val workerGroup: EventLoopGroup = Channels.pickBestEventLoopGroup()

		val connectionManager: ConnectionManager = InbConnectionManager()

		bootstrap
			.group(masterGroup, workerGroup)
			.channel(Channels.pickBestChannel())
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

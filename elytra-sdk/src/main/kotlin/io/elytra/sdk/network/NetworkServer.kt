package io.elytra.sdk.network

import com.flowpowered.network.ConnectionManager
import io.elytra.sdk.console.ElytraConsole
import io.elytra.sdk.network.pipeline.ChannelInitializerHandler
import io.elytra.sdk.network.pipeline.InbConnectionManager
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup

/**
 * Initializes a TCP server to handle the packets, codecs and packet handlers
 * dispatched into the server via a port.
 *
 * @param port tcp server port
 */
internal class NetworkServer(
	private val port: Int = 25565,
	private val sessionRegistry: SessionRegistry
) {

	private val bootstrap: ServerBootstrap = ServerBootstrap()

	/**
	 * Boot up the tcp server
	 */
	fun start() {
		val masterGroup: EventLoopGroup = Channels.pickBestEventLoopGroup()
		val workerGroup: EventLoopGroup = Channels.pickBestEventLoopGroup()

		val connectionManager: ConnectionManager = InbConnectionManager(sessionRegistry)


		bootstrap
			.group(masterGroup, workerGroup)
			.channel(Channels.pickBestChannel())
			.option(ChannelOption.SO_KEEPALIVE, true)
			.childOption(ChannelOption.TCP_NODELAY, true)
			.childHandler(ChannelInitializerHandler(connectionManager))

		val server = bootstrap.bind(port).sync()

		if (server.isSuccess) {
			ElytraConsole.send(
				StringBuilder("&2").append("Server running in port: ").append(port),
				StringBuilder("&2").append("Ready for connections!")
			)
		}

		server.channel().closeFuture().sync()
		masterGroup.shutdownGracefully()
		workerGroup.shutdownGracefully()
	}
}

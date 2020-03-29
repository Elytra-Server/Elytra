package io.elytra.sdk.network

import com.flowpowered.network.ConnectionManager
import io.elytra.sdk.network.pipeline.ChannelInitializerHandler
import io.elytra.sdk.network.pipeline.ElytraConnectionManager
import io.elytra.sdk.server.Elytra
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
		val connectionManager: ConnectionManager = ElytraConnectionManager(sessionRegistry)

		bootstrap
			.group(masterGroup, workerGroup)
			.channel(Channels.pickBestChannel())
			.option(ChannelOption.SO_KEEPALIVE, true)
			.childOption(ChannelOption.TCP_NODELAY, true)
			.childHandler(ChannelInitializerHandler(connectionManager))

		val server = bootstrap.bind(port).sync()

		if (server.isSuccess) {
			Elytra.console.info("&eServer running on port: $port")
			Elytra.console.info("&2Ready for connections")
		}

		server.channel().closeFuture().sync()
		masterGroup.shutdownGracefully()
		workerGroup.shutdownGracefully()
	}
}

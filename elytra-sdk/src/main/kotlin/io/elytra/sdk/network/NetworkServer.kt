package io.elytra.sdk.network

import com.flowpowered.network.ConnectionManager
import io.elytra.sdk.network.pipeline.ChannelInitializerHandler
import io.elytra.sdk.network.pipeline.ElytraConnectionManager
import io.elytra.sdk.server.Elytra
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

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
	private val serverExecutors: ExecutorService = Executors.newCachedThreadPool()

	/**
	 * Boot up the tcp server
	 */
	fun start() {
		val workerGroup: EventLoopGroup = Channels.pickBestEventLoopGroup()
		val connectionManager: ConnectionManager = ElytraConnectionManager(sessionRegistry)

		bootstrap
			.group(workerGroup)
			.channel(Channels.pickBestChannel())
			.option(ChannelOption.SO_KEEPALIVE, true)
			.childOption(ChannelOption.TCP_NODELAY, true)
			.childOption(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK , 32 * 1024)
			.childOption(ChannelOption.WRITE_BUFFER_LOW_WATER_MARK , 8 * 1024)
			.childHandler(ChannelInitializerHandler(connectionManager))

		val server = bootstrap.bind(port).sync()

		if (server.isSuccess) {
			Elytra.console.info("&eServer running on port: $port")
			Elytra.console.info("&2Ready for connections")
		}

		server.channel().closeFuture().sync()
		workerGroup.shutdownGracefully()
	}
}

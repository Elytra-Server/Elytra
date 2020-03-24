package io.inb.sdk.network.handler

import io.inb.api.network.Session
import io.inb.api.network.SessionManager
import io.inb.api.protocol.Packet
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler


class ServerHandler : SimpleChannelInboundHandler<Packet>() {
	override fun channelActive(ctx: ChannelHandlerContext) {
		val channel: Channel = ctx.channel()

		Session(channel)

		println("New session (${SessionManager.activeSessions()} total). Channel: $channel.")
	}

	override fun channelInactive(ctx: ChannelHandlerContext) { // Destroy session
		val channel: Channel = ctx.channel()
		SessionManager.closeSession(channel)
		println("Session closed (${SessionManager.activeSessions()} total). Channel: $channel.")
	}

	override fun channelRead0(ctx: ChannelHandlerContext, message: Packet?) {
		val session = SessionManager.getSession(ctx.channel())

		if (message != null) {
			session?.queueIncomingPackets(message)
		}
	}
}

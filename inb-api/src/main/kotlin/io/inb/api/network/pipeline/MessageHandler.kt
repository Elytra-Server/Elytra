package io.inb.api.network.pipeline

import com.flowpowered.network.ConnectionManager
import com.flowpowered.network.Message
import io.inb.api.network.InbConnectionManager
import io.inb.api.network.InbSession
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import java.util.concurrent.atomic.AtomicReference


class MessageHandler(
	private val session: AtomicReference<InbSession?> = AtomicReference(null),
	private val connectionManager: ConnectionManager
) : SimpleChannelInboundHandler<Message>() {

	override fun channelRead0(ctx: ChannelHandlerContext, msg: Message) {
		session.get()?.messageReceived(msg);
	}

	override fun channelActive(ctx: ChannelHandlerContext?) {
		val channel = ctx?.channel()
		val newSession = connectionManager.newSession(channel) as InbSession

		if (!session.compareAndSet(null, newSession)) {
			throw IllegalStateException("Session may not be set more than once");
		}

		newSession.onReady()
	}

	override fun channelInactive(ctx: ChannelHandlerContext?) {
		session.get()?.onDisconnect();
	}

	override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
		session.get()?.onInboundThrowable(cause);
	}
}

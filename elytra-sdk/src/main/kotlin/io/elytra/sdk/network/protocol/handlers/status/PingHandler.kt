package io.elytra.sdk.network.protocol.handlers.status

import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.handlers.ElytraMessageHandler
import io.elytra.sdk.network.protocol.message.DisconnectMessage
import io.elytra.sdk.network.protocol.message.status.PingMessage
import io.netty.channel.ChannelFutureListener

class PingHandler : ElytraMessageHandler<PingMessage>() {
	override fun handle(session: NetworkSession, message: PingMessage) {
		session.sendWithFuture(message)?.addListener(ChannelFutureListener.CLOSE)
	}
}

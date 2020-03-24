package io.inb.api.network

import com.flowpowered.network.Message
import com.flowpowered.network.protocol.AbstractProtocol
import com.flowpowered.network.session.BasicSession
import io.inb.api.entity.Player
import io.inb.api.network.pipeline.CodecsHandler
import io.inb.api.network.protocol.message.DisconnectMessage
import io.inb.api.network.protocol.packets.BasicPacket
import io.inb.api.network.protocol.packets.HandshakePacket
import io.inb.api.utils.Tickable
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandler
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque


class InbSession(channel: Channel) : BasicSession(channel, HandshakePacket()), Tickable {

	var state: State = State.HANDSHAKE
	var player: Player? = null
	var protocol: BasicPacket? = null

	private val messageQueue: BlockingQueue<Message> = LinkedBlockingDeque<Message>()

	override fun sendWithFuture(message: Message?): ChannelFuture? {
		if (!isActive) {
			return null
		}

		return super.sendWithFuture(message)
	}

	fun disconnect(reason: String) {
		sendWithFuture(DisconnectMessage(reason))
			?.addListener(ChannelFutureListener.CLOSE)
	}

	override fun disconnect() {
		disconnect("No reason specified.")
	}

	override fun tick() {
		var message: Message?
		while (messageQueue.poll().also { message = it } != null) {
			//TODO: Handle disconnected sessions

			super.messageReceived(message)
		}
	}

	public override fun setProtocol(protocol: AbstractProtocol?) {
		channel.flush()
		updatePipeline("codecs", CodecsHandler(protocol as BasicPacket))
		super.setProtocol(protocol)
	}

	private fun updatePipeline(key: String, handler: ChannelHandler) {
		channel.pipeline().replace(key, key, handler);
	}
}

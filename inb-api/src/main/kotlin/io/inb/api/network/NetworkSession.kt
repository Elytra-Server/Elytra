package io.inb.api.network

import com.flowpowered.network.Message
import com.flowpowered.network.MessageHandler
import com.flowpowered.network.protocol.AbstractProtocol
import com.flowpowered.network.session.BasicSession
import io.inb.api.InbServer
import io.inb.api.entity.Player
import io.inb.api.network.pipeline.CodecsHandler
import io.inb.api.network.protocol.PacketProvider
import io.inb.api.network.protocol.message.DisconnectMessage
import io.inb.api.network.protocol.message.login.LoginSuccessMessage
import io.inb.api.network.protocol.packets.BasicPacket
import io.inb.api.network.protocol.packets.HandshakePacket
import io.inb.api.utils.Asyncable
import io.inb.api.utils.Tickable
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandler
import io.netty.handler.codec.CodecException
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque


class NetworkSession(
	channel: Channel,
	var state: State = State.HANDSHAKE,
	var protocol: BasicPacket? = null,

	//TODO: Needs refactor, too many instances
	private val packetProvider: PacketProvider = PacketProvider(),
	@Volatile private var disconnect: Boolean = false
) : BasicSession(channel, HandshakePacket()), Tickable {

	var player: Player? = null

	private val messageQueue: BlockingQueue<Message> = LinkedBlockingDeque()

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
		updatePipeline("codecs", CodecsHandler(protocol as BasicPacket))
		super.setProtocol(protocol)
	}

	fun assignPlayer(player: Player){
		if(!isActive){ //Check if the player is disconnected
			return
		}

		this.player = player
		finalizeLogin(player)
		player.join(this)

		if (!isActive) {
			onDisconnect();
			return;
		}
	}

	private fun finalizeLogin(player: Player?){
		if (player != null) {
			send(LoginSuccessMessage(player.uuid.toString(), player.username))
		}

		setProtocol(packetProvider.playPacket)
	}

	private fun updatePipeline(key: String, handler: ChannelHandler) {
		channel.pipeline().replace(key, key, handler);
	}

	override fun onDisconnect() {
		disconnect = true
	}

	override fun onInboundThrowable(throwable: Throwable?) {
		if(throwable is CodecException){
			println("Error in inbound network: $throwable")
			return
		}

		disconnect("decode error: ${throwable?.message}")
	}

	override fun onHandlerThrowable(message: Message?, handle: MessageHandler<*, *>?, throwable: Throwable?) {
		println("Error while handling $message (${handle?.javaClass?.simpleName}) - $throwable")
	}

	override fun messageReceived(message: Message) {
		if(message is Asyncable){
			super.messageReceived(message)
			return
		}

		messageQueue.add(message)
	}

}

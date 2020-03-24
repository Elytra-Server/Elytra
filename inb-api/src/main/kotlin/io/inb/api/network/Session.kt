package io.inb.api.network

import io.inb.api.entity.Player
import io.inb.api.protocol.Packet
import io.inb.api.protocol.PacketHandlers
import io.inb.api.utils.Tickable
import io.inb.api.utils.extensions.kClass
import io.netty.channel.Channel
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque


class Session(val channel: Channel) : Tickable {

	var state: State = State.HANDSHAKE
	var player: Player? = null

	private val packetQueue: BlockingQueue<Packet> = LinkedBlockingDeque<Packet>()

	init {
		SessionManager.openSession(this)
	}

	fun queueIncomingPackets(packet: Packet) = packetQueue.add(packet)

	fun sendPacket(packet: Packet) = channel.writeAndFlush(packet)

	override fun tick() {
		var packet: Packet? = null

		while (packet != null) {
			PacketHandlers.getHandler(packet.kClass)?.handle(this, packet)
			packet = packetQueue.poll()
		}
	}
}

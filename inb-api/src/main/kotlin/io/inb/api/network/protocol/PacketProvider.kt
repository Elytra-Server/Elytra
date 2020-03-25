package io.inb.api.network.protocol

import io.inb.api.network.protocol.packets.HandshakePacket
import io.inb.api.network.protocol.packets.LoginPacket
import io.inb.api.network.protocol.packets.PlayPacket
import io.inb.api.network.protocol.packets.StatusPacket

data class PacketProvider(
	private val statusPacket: StatusPacket = StatusPacket(),
	private val loginPacket: LoginPacket = LoginPacket(),
	private val handshakePacket: HandshakePacket = HandshakePacket(),
	val playPacket: PlayPacket = PlayPacket()
)

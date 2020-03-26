package io.elytra.sdk.network.protocol

import io.elytra.sdk.network.protocol.packets.HandshakePacket
import io.elytra.sdk.network.protocol.packets.LoginPacket
import io.elytra.sdk.network.protocol.packets.PlayPacket
import io.elytra.sdk.network.protocol.packets.StatusPacket

/**
 * Provides the packet to the main game loop [io.inb.api.server.Server]
 * in order to make them locatable to the codecs
 */
data class PacketProvider(
        private val statusPacket: StatusPacket = StatusPacket(),
        private val loginPacket: LoginPacket = LoginPacket(),
        private val handshakePacket: HandshakePacket = HandshakePacket(),
        val playPacket: PlayPacket = PlayPacket()
)

package io.inb.api.protocol.packets

import io.inb.api.protocol.Packet

data class HandshakePacket(val username: String) : Packet

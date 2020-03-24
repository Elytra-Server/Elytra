package io.inb.sdk.protocol.packets

import io.inb.api.protocol.Packet

data class HandshakePacket(val username: String) : Packet

package io.inb.api.protocol.packets

import io.inb.api.protocol.Packet

data class DisconnectPacket(val message: String) : Packet

package io.inb.api.protocol.packets

import io.inb.api.protocol.Packet

data class LoginPacket(val username: String, val id: Int) : Packet

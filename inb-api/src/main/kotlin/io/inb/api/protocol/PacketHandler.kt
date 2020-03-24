package io.inb.api.protocol

import io.inb.api.network.Session

interface PacketHandler<T : Packet> {
	fun handle(session: Session, body: T)
}

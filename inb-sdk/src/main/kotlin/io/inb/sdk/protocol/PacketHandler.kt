package io.inb.sdk.protocol

import io.inb.sdk.network.Session

interface PacketHandler<T : Packet> {
	fun handle(session: Session, body: T)
}

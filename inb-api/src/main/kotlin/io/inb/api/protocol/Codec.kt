package io.inb.api.protocol

import io.netty.buffer.ByteBuf

interface Codec <T : Packet> {

	fun encode(buffer: ByteBuf) : T

	fun decode() : ByteBuf

}

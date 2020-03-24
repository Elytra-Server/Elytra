package io.inb.api.protocol

import io.netty.buffer.ByteBuf
import kotlin.reflect.KClass

interface Codec <T : Packet> {

	var type: KClass<T>
	var id: Short

	fun encode(packet: T) : ByteBuf

	fun decode(buffer: ByteBuf) : T
}

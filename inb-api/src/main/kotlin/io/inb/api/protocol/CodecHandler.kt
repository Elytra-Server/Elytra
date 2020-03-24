package io.inb.api.protocol

import io.inb.api.protocol.codecs.DisconnectCodec
import io.inb.api.protocol.codecs.HandshakeCodec
import io.inb.api.protocol.codecs.LoginCodec
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

//TODO: Use concurrent collections
object CodecHandler {

	private val codecs = mutableListOf<Codec<*>>()
	private val packetCodecs = mutableMapOf<KClass<out Packet>, Codec<*>>()

	init {
		register(HandshakeCodec::class)
		register(LoginCodec::class)
		register(DisconnectCodec::class)
	}

	private fun <T : Packet, C : Codec<T>> register(clazz: KClass<C>) {
		val codec: Codec<T> = clazz.createInstance()
		codecs.add(codec)
		packetCodecs[codec.type] = codec
	}

	fun getCodec(id: Int): Codec<*>? = codecs.find { it.id == id}

	fun <T : Packet> getCodec(clazz: KClass<T>): Codec<T> = packetCodecs[clazz] as Codec<T>
}

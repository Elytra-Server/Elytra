package io.inb.api.protocol

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

object PacketHandlers {

	private val handlers: ConcurrentMap<KClass<out Packet>, PacketHandler<*>> = ConcurrentHashMap()

	private fun <T : Packet> register(clazz: KClass<T>, packetHandler: KClass<PacketHandler<T>>){
		val handler : PacketHandler<T> = packetHandler.createInstance()
		handlers[clazz] = handler
	}

	fun <T : Packet> getHandler(clazz: KClass<T>) : PacketHandler<T>? {
		return handlers[clazz] as PacketHandler<T>?
	}
}

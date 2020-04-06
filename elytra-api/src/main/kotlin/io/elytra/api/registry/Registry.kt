package io.elytra.api.registry

interface Registry <T, K> {

    fun add(target: T)

    fun remove(target: T)

    fun get(target: K): T?

    fun has(target: K): Boolean

    fun iterator(): Iterator<T>

    fun size(): Int

    fun clear()
}

package io.elytra.api.registry

interface Registry <T, K> {

    suspend fun add(target: T): Unit

    suspend fun remove(target: T): Unit

    fun get(target: K): T?

    fun has(target: K): Boolean

    fun iterator(): Iterator<T>

    fun size(): Int

    fun clear()
}

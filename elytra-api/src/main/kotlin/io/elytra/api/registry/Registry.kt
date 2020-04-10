package io.elytra.api.registry

/**
 * Provides basic crud operations
 *
 * @param [K] key
 * @param [V] value
 */
interface Registry <K, V> {

    suspend fun add(target: V)

    suspend fun remove(target: V)

    fun get(key: K): V?

    fun has(key: K): Boolean

    fun iterator(): Iterator<V>

    fun size(): Int

    fun clear()
}

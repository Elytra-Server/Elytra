package io.elytra.api.registry

import java.util.stream.Stream

interface Registry <T, K> {

	fun add(record: T)

	fun remove(record: T)

	fun get(record: K): T?

	fun stream(): Stream<T>

	fun size(): Int

	fun clear()

}

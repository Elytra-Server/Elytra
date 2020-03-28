package io.elytra.sdk.server

import io.elytra.api.entity.Player
import io.elytra.api.registry.Registry
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class PlayerRegistry(
	private val players: Set<Player> = ConcurrentHashMap.newKeySet(),
	private var currentId: AtomicInteger = AtomicInteger(1)
) : Registry<Player, String>{

	override fun add(target: Player) {
		players.plus(target)
		currentId.getAndIncrement()

		println("current session id - " + currentId.get())
	}

	override fun remove(target: Player) {
		players.minus(target)
		currentId.getAndDecrement()
	}

	override fun get(target: String): Player? {
		return players.first { player -> player.username == target };
	}

	override fun iterator(): Iterator<Player> {
		return players.iterator()
	}

	override fun size(): Int {
		return players.size
	}

	override fun clear() {

	}

}

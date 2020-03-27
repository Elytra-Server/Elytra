package io.elytra.sdk.server

import io.elytra.api.entity.Player
import io.elytra.api.registry.Registry
import io.elytra.sdk.network.NetworkSession
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.stream.Stream

class PlayerRegistry(
	private val sessions: ConcurrentMap<NetworkSession, Boolean> = ConcurrentHashMap()
) : Registry<Player, String>{

	override fun add(target: Player) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun remove(target: Player) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun get(target: String): Player? {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun iterator(): Stream<Player> {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun size(): Int {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun clear() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

}

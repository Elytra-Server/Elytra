package io.inb.server

import io.inb.api.entity.Player
import io.inb.api.registry.Registry
import io.inb.api.utils.Tickable
import io.inb.network.NetworkSession
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.stream.Stream

class PlayerRegistry(
	private val sessions: ConcurrentMap<NetworkSession, Boolean> = ConcurrentHashMap()
) : Registry<Player, String>{

	override fun add(record: Player) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun remove(record: Player) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun get(record: String): Player? {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun stream(): Stream<Player> {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun size(): Int {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun clear() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

}

package io.inb

import io.inb.api.server.Server
import io.inb.server.ElytraServer

class Elytra {
	companion object {
		fun getServer() : ElytraServer = ElytraServer()
	}
}

fun main() {
	Elytra.getServer().boot()
}

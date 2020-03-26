package io.elytra.api.world


enum class WorldType(val id: Int, val pretyName: String) {
	NORMAL(0,"normal"),
	FLAT(1,"flat"),
	NETHER(2,"nether"),
	THE_END(3,"end"),
	DEBUG(4,"test")
}

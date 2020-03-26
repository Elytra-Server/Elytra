package io.inb.api.world


enum class WorldType(val id: Int, val pretyName: String) {
	NORMAL(0,"normal"),
	FLAT(1,"flat"),
	NETHER(2,""),
	THE_END(3,""),
	DEBUG(4,"test")
}

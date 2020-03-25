package io.inb.api.world


enum class WorldType(val id: Int, val pretyName: String) {
	DEFAULT(0,""),
	FLAT(1,""),
	LARGE_BIOMES(2,""),
	AMPLIFIED(3,""),
	CUSTOMIZED(3,""),
	DEBUG_WORLD(4,"")
}

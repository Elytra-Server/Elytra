package io.elytra.api.world

interface WorldLoadStrategy {

	fun load(path: String) : World

}

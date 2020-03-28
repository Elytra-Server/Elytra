package io.elytra.api.world

import java.util.*

interface World {
	var uuid: UUID//Maybe remove this, i think we can use this to identify
	var name: String
	var difficulty: Difficulty
	var type: WorldType
	var mode: WorldMode
}

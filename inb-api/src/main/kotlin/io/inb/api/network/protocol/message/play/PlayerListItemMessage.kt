package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message

enum class Action(val value: Int) {
	ADD_PLAYER(0),
	UPDATE_GAME_MODE(1),
	UPDATE_LATENCY(2),
	UPDATE_DISPLAY_NAME(3),
	REMOVE_PLAYER(4)
}

data class PlayerListItemMessage(
	val invulnerable: Boolean ,val flying: Boolean ,val allowFlying: Boolean ,
	val creativeMode: Boolean,val flySpeed: Float, val walkSpeed: Float
) : Message

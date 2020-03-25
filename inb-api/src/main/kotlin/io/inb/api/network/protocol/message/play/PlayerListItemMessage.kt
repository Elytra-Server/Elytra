package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message

enum class Action() {
	ADD_PLAYER,
	UPDATE_GAME_MODE,
	UPDATE_LATENCY,
	UPDATE_DISPLAY_NAME,
	REMOVE_PLAYER
}

data class PlayerListItemMessage(
	val invulnerable: Boolean ,val flying: Boolean ,val allowFlying: Boolean ,
	val creativeMode: Boolean,val flySpeed: Float, val walkSpeed: Float
) : Message

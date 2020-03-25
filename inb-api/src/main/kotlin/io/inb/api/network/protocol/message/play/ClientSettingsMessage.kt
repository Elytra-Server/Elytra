package io.inb.api.network.protocol.message.play

import com.flowpowered.network.Message

data class ClientSettingsMessage(
	val lang: String,
	val view: Int,
	val chatVisibility: Int,
	val enableColors: Boolean,
	val modelPartFlags: Int,
	val mainHand: Int
) : Message

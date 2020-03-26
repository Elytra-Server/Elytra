package io.inb.network.protocol.message.play

import com.flowpowered.network.Message

data class ClientSettingsMessage(
	val lang: String,
	val view: Byte,
	val chatVisibility: Int,
	val enableColors: Boolean,
	val modelPartFlags: Short,
	val mainHand: Int
) : Message

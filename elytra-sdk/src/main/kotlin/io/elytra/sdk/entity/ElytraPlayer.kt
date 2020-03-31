package io.elytra.sdk.entity

import com.flowpowered.network.Message
import com.mojang.authlib.GameProfile
import io.elytra.api.chat.ChatMode
import io.elytra.api.chat.TextComponent
import io.elytra.api.entity.Player
import io.elytra.api.entity.PlayerMode
import io.elytra.api.utils.asJson
import io.elytra.api.world.Position
import io.elytra.api.world.enums.GameMode
import io.elytra.sdk.network.NetworkSession
import io.elytra.sdk.network.protocol.message.DisconnectMessage
import io.elytra.sdk.network.protocol.message.play.OutboundChatMessage
import io.elytra.sdk.server.Elytra

data class ElytraPlayer(
	var id: Int,
	var sessionId: String,
	override var displayName: String,
	override var gameProfile: GameProfile?,
	override var mode: PlayerMode,
	override var online: Boolean,
	override var banned: Boolean,
	override var exp: Int,
	override var expLevel: Int,
	override var gamemode: GameMode = GameMode.SURVIVAL,
	override var position: Position
) : Player {

	private fun session(): NetworkSession? {
		return Elytra.server.sessionRegistry.get(sessionId)
	}

	override fun kick(reason: String) {
		session()?.send(DisconnectMessage(reason))
	}

	override fun sendMessage(message: String) {
		sendMessage(TextComponent(message))
	}

	override fun sendMessage(textComponent: TextComponent) {
		sendPacket(OutboundChatMessage(textComponent.asJson(), ChatMode.PLAYER))
	}

	fun sendPacket(packet: Message) {
		session()?.sendWithFuture(packet)
	}
}

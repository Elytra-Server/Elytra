package io.elytra.sdk.entity

import com.flowpowered.network.Message
import com.mojang.authlib.GameProfile
import io.elytra.api.chat.ChatComponent
import io.elytra.api.chat.ChatMode
import io.elytra.api.entity.Player
import io.elytra.api.entity.PlayerMode
import io.elytra.api.utils.asJson
import io.elytra.api.world.enums.GameMode
import io.elytra.api.world.Position
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
	override var position: Position = Position.EMPTY,
	override var gamemode: GameMode = GameMode.SURVIVAL
) : Player {

	private fun session(): NetworkSession? {
		return Elytra.server.sessionRegistry.get(sessionId)
	}

	fun sendPacket(message: Message){
		session()?.send(message)
	}

	override fun kick(reason: String) {
		session()?.send(DisconnectMessage(reason))
	}

	override fun sendMessage(message: String) {
		sendMessage(ChatComponent(message))
	}

	override fun sendMessage(chatComponent: ChatComponent) {
		sendPacket(OutboundChatMessage(chatComponent.asJson(), ChatMode.PLAYER))
	}

	private fun sendPacket(packet: Message) {
		session()?.sendWithFuture(packet)
	}
}

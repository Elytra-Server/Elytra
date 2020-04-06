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
import io.elytra.sdk.network.protocol.message.play.outbound.DisconnectMessage
import io.elytra.sdk.network.protocol.message.play.outbound.OutboundChatMessage
import io.elytra.sdk.network.protocol.message.play.outbound.SpawnPlayerMessage
import io.elytra.sdk.server.Elytra

data class ElytraPlayer(
    var id: Int,
    var sessionId: String,
    override var displayName: String,
    override var gameProfile: GameProfile, // TODO: Remove since its an internal thing
    override var mode: PlayerMode,
    override var online: Boolean,
    override var banned: Boolean,
    override var gamemode: GameMode = GameMode.SURVIVAL,
    override var position: Position
) : Player, ElytraEntity(0) {

    override fun tick() {
        TODO("not implemented")
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

    override fun spawn() {
        spawnAt(position)
    }

    override fun spawnAt(position: Position) {
        val x: Double = position.x
        val y: Double = position.y
        val z: Double = position.z
        val yaw: Float = position.yaw
        val pitch: Float = position.pitch

        sendPacket(SpawnPlayerMessage(id, gameProfile.id, x, y, z, yaw, pitch))
    }

    override fun sendPacket(packet: Message) {
        session()?.sendWithFuture(packet)
    }

    private fun session(): NetworkSession? {
        return Elytra.server.sessionRegistry.get(sessionId)
    }
}

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
import io.elytra.sdk.network.protocol.message.play.outbound.ChangeGameStateMessage
import io.elytra.sdk.network.protocol.message.play.outbound.DisconnectMessage
import io.elytra.sdk.network.protocol.message.play.outbound.OutboundChatMessage
import io.elytra.sdk.server.Elytra

data class ElytraPlayer(
    var id: Int,
    var sessionId: String,
    override var displayName: String,
    override var gameProfile: GameProfile, // TODO: Remove since its an internal thing
    override var mode: PlayerMode,
    override var online: Boolean,
    override var banned: Boolean,
    override var position: Position
) : Player, ElytraEntity(0) {

    override var gamemode: GameMode = GameMode.CREATIVE
        set(value) {
            field = value
            sendPacket(ChangeGameStateMessage(ChangeGameStateMessage.Reason.GAMEMODE, value.value.toFloat()))
        }

    override fun kick(reason: String) {
        session()?.send(DisconnectMessage(reason))
    }

    override fun sendMessage(message: String) {
        val textComponent = TextComponent(message)
        textComponent.text = textComponent.text.replace('&', '§')

        sendPacket(OutboundChatMessage(textComponent.asJson(), ChatMode.PLAYER))
    }

    override fun sendMessage(vararg messages: String) {
        val builder = StringBuilder()

        for (arg in messages) {
            builder.append(" ").append(arg)
        }

        sendMessage(builder.toString())
    }

    override fun sendPacket(packet: Message) {
        session()?.sendWithFuture(packet)
    }

    private fun session(): NetworkSession? {
        return Elytra.server.sessionRegistry.get(sessionId)
    }
}

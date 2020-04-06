package io.elytra.api.entity

import com.flowpowered.network.Message
import com.mojang.authlib.GameProfile
import io.elytra.api.chat.TextComponent
import io.elytra.api.world.Position
import io.elytra.api.world.enums.GameMode

interface Player {
    var displayName: String
    var gameProfile: GameProfile

    var mode: PlayerMode
    var online: Boolean
    var banned: Boolean

    var gamemode: GameMode
    var position: Position

    fun kick(reason: String)

    fun sendMessage(message: String)

    fun sendMessage(textComponent: TextComponent)

    fun sendPacket(packet: Message)
}

enum class PlayerMode {
    PREMIUM,
    OFFLINE
}

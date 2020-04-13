package io.elytra.api.entity

import com.flowpowered.network.Message
import com.mojang.authlib.GameProfile
import io.elytra.api.command.CommandIssuer
import io.elytra.api.enum.Effect
import io.elytra.api.world.Position
import io.elytra.api.world.enums.GameMode

interface Player : CommandIssuer, Entity {
    var displayName: String
    var gameProfile: GameProfile

    var mode: PlayerMode
    var online: Boolean
    var banned: Boolean

    var gamemode: GameMode

    fun kick(reason: String)

    fun playEffect(position: Position, effect: Effect, metadata: Int = 0)

    fun sendPacket(packet: Message)
}

enum class PlayerMode {
    PREMIUM,
    OFFLINE
}

package io.elytra.api.entity

import com.flowpowered.network.Message
import com.mojang.authlib.GameProfile
import io.elytra.api.command.CommandSender
import io.elytra.api.world.Position
import io.elytra.api.world.World
import io.elytra.api.world.enums.GameMode

interface Player : CommandSender {
    var displayName: String
    var gameProfile: GameProfile

    var mode: PlayerMode
    var online: Boolean
    var banned: Boolean

    var gamemode: GameMode
    var position: Position

    var world: World

    fun kick(reason: String)

    fun sendPacket(packet: Message)
}

enum class PlayerMode {
    PREMIUM,
    OFFLINE
}

package io.elytra.sdk.network.protocol.message.login

import com.flowpowered.network.Message
import com.mojang.authlib.GameProfile
import io.elytra.api.utils.Asyncable

data class LoginStartMessage(val gameProfile: GameProfile) : Message, Asyncable
